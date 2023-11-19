package com.taufik.ecommercelist.data.model

data class Profile(
    val photo: Int,
    val name: String,
    val email: String,
    val socialMedia: List<String>? = null
)
