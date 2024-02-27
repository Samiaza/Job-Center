package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Vacancy (
    val profession: Profession,
    val level: VacancyLevel,
    val salary: Int
)