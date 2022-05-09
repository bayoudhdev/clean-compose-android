package com.demo.kaamelott.domain.models.extensions

import com.demo.kaamelott.domain.models.DetailsDomain
import com.demo.kaamelott.domain.models.QuoteDomain
import com.demo.kaamelott.presentation.models.Metadata
import com.demo.kaamelott.presentation.models.Quote

fun QuoteDomain.toModel() = Quote(
    quote = this.quote,
    metaData = this.information.toModel()
)

fun DetailsDomain.toModel() = Metadata(
    actor = actor,
    personage = personage,
    episode = episode,
    author = author,
    season = season
)
