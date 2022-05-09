package com.demo.kaamelott.presentation.models

import com.demo.kaamelott.R

fun BookSeason.getKaamelottImageId(): Int {
    return when (this) {
        BookSeason.BOOK_1 -> R.drawable.kaamelott_quote
        BookSeason.BOOK_2 -> R.drawable.kaamelott_quote_2
        BookSeason.BOOK_3 -> R.drawable.kaamelott_quote_3
        BookSeason.BOOK_4 -> R.drawable.kaamelott_quote_4
        BookSeason.BOOK_5 -> R.drawable.kaamelott_quote_5
        BookSeason.BOOK_6 -> R.drawable.kaamelott_quote_6
    }
}
