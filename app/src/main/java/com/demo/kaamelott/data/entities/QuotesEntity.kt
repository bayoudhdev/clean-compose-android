package com.demo.kaamelott.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuotesEntity(
    val status: Int = -1,
    @SerialName("citation")
    val quotes: List<QuoteEntity> = emptyList()
)
