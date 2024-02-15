package com.example.librarymanagement

import java.util.Date


data class BookIssueState(
    val bookID: String? = null,
    val returned: Boolean = false,
    val issuedDate: Date? = null,
    val returnDate: Date? = null
)