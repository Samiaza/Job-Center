package com.example.model

import kotlinx.serialization.SerialName

enum class CompanyActivity(val value: String) {
    @SerialName("IT") IT("IT"),
    @SerialName("banking")BANK("Banking"),
    @SerialName("public services")PUB("Public services")
}