package com.shadow.kotlinpractice.domain.model

/**
 * Data class that captures user information for logged in users retrieved from AccountRepository
 */
data class LoggedInUser(
    val userId: String,
    val displayName: String
)
