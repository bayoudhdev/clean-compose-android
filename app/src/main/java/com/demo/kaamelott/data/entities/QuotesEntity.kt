package com.demo.kaamelott.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuotesEntity(
    @SerialName("status")
    val status: Int,
    @SerialName("citation")
    val quotes: List<QuoteEntity> = emptyList(),
    @SerialName("error")
    val error: String? = null
)
