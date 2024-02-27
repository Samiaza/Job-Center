package com.example.model

data class Candidate(
    var activity: CompanyActivity? = null,
    var profession: Profession? = null,
    var level: VacancyLevel? = null,
    var salary: Salary? = null,
) {
    override fun toString(): String {
        return "Activity : ${activity?.value?:"All"}. Profession: ${profession?.value?:"All"}. " +
                "Level: ${level?.value?:"All"}. Salary: ${salary?.valueStr?:"All"}."
    }
}