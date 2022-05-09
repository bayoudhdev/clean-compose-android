package com.demo.kaamelott.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RandomQuoteEntity(
    @SerialName("status")
    val status: Int,
    @SerialName("citation")
    val quote: QuoteEntity,
)
