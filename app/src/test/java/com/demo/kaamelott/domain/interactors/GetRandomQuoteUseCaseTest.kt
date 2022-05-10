package com.demo.kaamelott.domain.interactors

import com.demo.kaamelott.MainCoroutineRule
import com.demo.kaamelott.domain.repositories.QuotesRepository
import com.demo.kaamelott.mockQuoteDomain
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

class GetRandomQuoteUseCaseTest {

    @get:Rule
    val coroutineRule: MainCoroutineRule = MainCoroutineRule()

    private val repository: QuotesRepository = mockk {}

    private lateinit var useCase: GetRandomQuoteUseCase

    @Before
    fun setup() {
        useCase = GetRandomQuoteUseCase(
            repository,
            coroutineRule.testDispatcher
        )
    }

    @Test
    fun `get random quote when everything is fine`() = runTest {
        // Given
        coEvery { repository.getRandomQuote() } returns Result.success(mockQuoteDomain)

        // When
        val result = useCase(Unit)

        // Then
        coVerify(exactly = 1) {
            repository.getRandomQuote()
        }

        assertTrue(result.isSuccess)
        val quote = result.getOrNull()
        assertNotNull(quote)
        assertEquals(mockQuoteDomain.quote, quote?.getOrNull()?.quote.orEmpty())
    }

    @Ignore
    fun `get random quote when fetch failed`() = runTest {
        // Given
        coEvery { repository.getRandomQuote()} returns Result.failure(Throwable("Can't find quote in response"))

        // When
        val result = useCase(Unit)

        // Then
        coVerify(exactly = 1) {
            repository.getRandomQuote()
        }
        assertTrue(result.isFailure)
        assertEquals("Can't find quote in response", result.exceptionOrNull()?.message)
    }
}