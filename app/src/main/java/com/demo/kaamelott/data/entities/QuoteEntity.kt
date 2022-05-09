package com.demo.kaamelott.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuoteEntity(
    @SerialName("citation")
    val quote: String,
    @SerialName("infos")
    val information: DetailsEntity
)
