package com.demo.kaamelott.core.bases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

abstract class BaseUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    /** Executes the base useCase synchronously or asynchronously and returns a [Result].
     * @param parameters the input parameters to run the use case with.
     * @return a [Result].
     */
    suspend operator fun invoke(parameters: P): Result<R> {
        return try {
            withContext(coroutineDispatcher) {
                execute(parameters).let {
                    Result.success(it)
                }
            }
        } catch (e: Exception) {
            Timber.d(e)
            Result.failure(e)
        }
    }

    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): R
}
