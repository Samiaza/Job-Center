package com.example.model

import kotlinx.serialization.SerialName

enum class VacancyLevel(val value: String) {
    @SerialName("junior") JUN("Junior"),
    @SerialName("middle") MID("Middle"),
    @SerialName("senior") SEN("Senior")
}