package com.demo.kaamelott.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailsEntity(
    @SerialName("acteur")
    val actor: String,
    @SerialName("personnage")
    val personage: String? = null,
    @SerialName("auteur")
    val author: String,
    @SerialName("saison")
    val season: String,
    @SerialName("episode")
    val episode: String
)
