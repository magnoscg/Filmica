package com.hispacode.filmica

data class Film(

        val id: String = "",
        val title: String = "No title",
        val genre: String = "No genre",
        val rating: Float = 0.0f,
        val overView: String = "No Overview",
        val date: String = "1999-01-1"
)