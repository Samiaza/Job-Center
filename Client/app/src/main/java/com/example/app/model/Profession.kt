package com.example.app.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.JsonNames

enum class Profession(val value: String) {
    @SerializedName("developer") @SerialName("developer") DEV("Developer"),
    @OptIn(ExperimentalSerializationApi::class)
    @JsonNames("qa", "QA") QA("QA"),
    @OptIn(ExperimentalSerializationApi::class)
    @JsonNames("PM", "pm") PM("PM"),
    @SerializedName("analyst") @SerialName("analyst") ANAL("Analyst"),
    @SerializedName("designer") @SerialName("designer") DES("Designer");

    companion object {
        fun fromValue(value: String): Profession? {
            return values().find { it.value == value }
        }
    }
}