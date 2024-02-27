package com.example.app.model

import kotlinx.serialization.Serializable

@Serializable
data class Contacts(
    val phone: String,
    val email: String
)