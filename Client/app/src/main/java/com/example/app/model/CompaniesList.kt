package com.example.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompaniesList(
    @SerialName("listOfCompanies") val companies: List<Company>
)