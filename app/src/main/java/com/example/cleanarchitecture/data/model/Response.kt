package com.example.cleanarchitecture.data.model

data class Response(
    val currentCount: Int = 0,
    val `data`: List<content> = emptyList(),
    val matchCount: Int = 0,
    val page: Int = 0,
    val perPage: Int =0,
    val totalCount: Int=0
)

data class content(
    val address : String="",
    val centerName : String=""
)