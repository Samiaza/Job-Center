package com.example.model

import kotlinx.serialization.SerialName

enum class EducationType(val value: String) {
    @SerialName("higher") HIGH("higher"),
    @SerialName("secondary special") SEC_SP("secondary special"),
    @SerialName("secondary") SEC("secondary")
}