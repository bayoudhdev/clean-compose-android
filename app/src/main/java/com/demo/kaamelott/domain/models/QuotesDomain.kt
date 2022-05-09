package com.demo.kaamelott.domain.models

data class QuotesDomain(val quotes: List<QuoteDomain> = emptyList())

data class QuoteDomain(
    val quote: String,
    val information: DetailsDomain
)

data class DetailsDomain(
    val actor: String,
    val personage: String,
    val author: String,
    val season: String,
    val episode: String
)
