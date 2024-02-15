package com.example.librarymanagement


data class User(
    val rollNo: String? = null,
    val username: String? = null,
    val isTeacher: Boolean? = false,
    val bookHistory: List<String> = arrayListOf()
)