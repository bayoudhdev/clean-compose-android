package com.demo.kaamelott.presentation.models

data class Quote(
    val quote: String,
    val metaData: Metadata
)

data class Metadata(
    val actor: String,
    val personage: String,
    val author: String,
    val season: String,
    val episode: String
)
