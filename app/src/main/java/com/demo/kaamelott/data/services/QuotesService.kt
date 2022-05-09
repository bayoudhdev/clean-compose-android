package com.demo.kaamelott.data.services

import com.demo.kaamelott.data.entities.QuoteEntity
import com.demo.kaamelott.data.entities.QuotesEntity
import com.demo.kaamelott.data.entities.RandomQuoteEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface QuotesService {
    /**
     * Get a random quote
     *
     * Responses:
     *  - 200: Request is successful, response contains the random quote
     *  - 404: Unrecoverable error from the service
     *
     * @return [QuoteEntity]
     */
    @GET("/api/random")
    suspend fun getRandomQuote(): Response<RandomQuoteEntity>

    /**
     * Get all quotes
     *
     * Responses:
     *  - 200: Request is successful, response contains the quotes
     *  - 404: Unrecoverable error from the service
     *
     * @return [QuotesEntity]
     */
    @GET("/all")
    suspend fun getAllQuotes(): Response<QuotesEntity>

    /**
     * Get a random quote by personage
     *
     * Responses:
     *  - 200: Request is successful, response contains the random quote
     *  - 404: Unrecoverable error from the service
     *
     * @param personage name of the personage in the book
     * @return [QuoteEntity]
     */
    @GET("/all/personnage/{personage}")
    suspend fun getRandomQuoteByPersonage(
        @Path("personage") personage: String
    ): Response<QuoteEntity>

    /**
     * Get all quotes by personage
     *
     * Responses:
     *  - 200: Request is successful, response contains all quotes
     *  - 404: Unrecoverable error from the service
     *
     * @param personage name of the personage in the book
     * @return [QuotesEntity]
     */
    @GET("/all/personnage/{personage}")
    suspend fun getAllQuotesByPersonage(
        @Path("personage") personage: String
    ): Response<QuotesEntity>

    /**
     * Get a random quote by book
     *
     * Responses:
     *  - 200: Request is successful, response contains the random quote
     *  - 404: Unrecoverable error from the service
     *
     * @param book a book number
     * @return [QuoteEntity]
     */
    @GET("/random/livre/{book}")
    suspend fun getRandomQuoteByBook(
        @Path("book") book: String
    ): Response<QuoteEntity>

    /**
     * Get all quotes by book
     *
     * Responses:
     *  - 200: Request is successful, response contains the all quotes
     *  - 404: Unrecoverable error from the service
     *
     * @param book a book number
     * @return [QuotesEntity]
     */
    @GET("/all/livre/{book}")
    suspend fun getAllQuotesByBook(
        @Path("book") book: String
    ): Response<QuotesEntity>

    /**
     * Get all quotes by book and personage
     *
     * Responses:
     *  - 200: Request is successful, response contains the all quotes
     *  - 404: Unrecoverable error from the service
     *
     * @param book a book number
     * @param personage name of the personage in the book
     * @return [QuotesEntity]
     */
    @GET("/all/livre/{book}/personnage/{personage}")
    suspend fun getCharacterQuoteByBook(
        @Path("book") book: Int,
        @Path("personage") personage: String
    ): Response<QuotesEntity>
}
