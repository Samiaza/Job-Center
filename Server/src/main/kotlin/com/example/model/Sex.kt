package com.example.model

import kotlinx.serialization.SerialName

enum class Sex(val value: String) {
    @SerialName("male") MALE("male"),
    @SerialName("female") FEM("female")
}