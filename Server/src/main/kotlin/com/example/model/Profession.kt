package com.example.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.JsonNames

enum class Profession(val value: String) {
    @SerialName("developer") DEV("Developer"),
    @OptIn(ExperimentalSerializationApi::class)
    @JsonNames("qa", "QA") QA("QA"),
    @OptIn(ExperimentalSerializationApi::class)
    @JsonNames("PM", "pm") PM("PM"),
    @SerialName("analyst") ANAL("Analyst"),
    @SerialName("designer") DES("Designer");

    companion object {
        fun fromValue(value: String): Profession? {
            return entries.find { it.value == value }
        }
    }
}