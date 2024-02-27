package com.example.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.Company

@Serializable
data class CompaniesList(
    @SerialName("listOfCompanies") val companies: List<Company>
)