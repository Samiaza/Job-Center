package com.example.app.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName

enum class VacancyLevel(val value: String) {
    @SerializedName("junior") @SerialName("junior") JUN("Junior"),
    @SerializedName("middle") @SerialName("middle") MID("Middle"),
    @SerializedName("senior") @SerialName("senior") SEN("Senior")
}