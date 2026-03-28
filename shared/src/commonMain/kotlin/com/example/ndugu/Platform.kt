package com.example.ndugu

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform