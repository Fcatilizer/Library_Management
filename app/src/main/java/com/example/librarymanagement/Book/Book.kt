package com.example.librarymanagement.Book

data class Book(
    val isbn: String? = null,
    val bookTitle: String? = null,
    val author: String? = null,
    val year: Int = 0,
    val bookIssued: Boolean = false
)
