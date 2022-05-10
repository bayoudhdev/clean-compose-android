package com.demo.kaamelott

import com.demo.kaamelott.domain.models.DetailsDomain
import com.demo.kaamelott.domain.models.QuoteDomain

private val mockDetailsDomain = DetailsDomain(
    personage = "Angharad",
    season = "Livre I",
    author = "Alexandre Astier",
    episode = "37 : La Romance de Lancelot",
    actor = "Vanessa Guedj"
)

internal val mockQuoteDomain = QuoteDomain(
    quote = "Hé ben, si un jour j’oublie que je suis boniche, vous serez gentil de me le rappeler !",
    information = mockDetailsDomain
)