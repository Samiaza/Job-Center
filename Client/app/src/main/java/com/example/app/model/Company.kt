package com.example.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Company (
    val id: Int?,
    val name: String,
    @SerialName("field_of_activity")val activity: CompanyActivity?,
    val vacancies: List<Vacancy>?,
    val contacts: String?
)




