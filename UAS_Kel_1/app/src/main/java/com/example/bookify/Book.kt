package com.example.bookify

data class Book(
    val title: String? = null,
    val author: String? = null,
    val imageUrl: String? = null // Image URL from Firebase Storage
)
