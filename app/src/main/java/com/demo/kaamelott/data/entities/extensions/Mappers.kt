package com.demo.kaamelott.data.entities.extensions

import com.demo.kaamelott.data.entities.DetailsEntity
import com.demo.kaamelott.data.entities.QuoteEntity
import com.demo.kaamelott.data.entities.QuotesEntity
import com.demo.kaamelott.domain.models.DetailsDomain
import com.demo.kaamelott.domain.models.QuoteDomain
import com.demo.kaamelott.domain.models.QuotesDomain

fun QuotesEntity.toDomain() = QuotesDomain(
    quotes = this.quotes.map { it.toDomain() }
)

fun QuoteEntity.toDomain() = QuoteDomain(
    quote = this.quote,
    information = this.information.toDomain()
)

fun DetailsEntity.toDomain() = DetailsDomain(
    actor = this.actor,
    personage = this.personage,
    episode = this.episode,
    author = this.author,
    season = this.season
)
