package com.demo.kaamelott.presentation.models

enum class BookSeason(val bookId: Int) {
    BOOK_1(1),
    BOOK_2(2),
    BOOK_3(3),
    BOOK_4(4),
    BOOK_5(5),
    BOOK_6(6);

    companion object {

        fun getByBook(bookId: Int) = values().find { it.bookId == bookId } ?: BOOK_1
    }
}
