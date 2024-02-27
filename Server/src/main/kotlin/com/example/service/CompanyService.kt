package com.example.service

import com.example.model.Profession
import com.example.repository.CompanyRepository
import org.example.Company
import com.example.model.Vacancy

class CompanyService(private val repository: CompanyRepository) {
    fun getCompaniesList(): List<Company> {
        return repository.getAllCompanies()
    }

    fun getCompany(id: Int): Company? {
        return repository.getCompany(id)
    }

    fun getVacancies(): Map<String, List<String>?> {
        return repository.getAllCompanies()
            .associate { company -> "${company.name} (id = ${company.id})" to
                    company.vacancies?.map{ it.profession.value } }
    }

    fun getVacancy(companyName: String, vacancyName: String): Vacancy? {
        val profession = try { Profession.fromValue(vacancyName) }
                            catch (_: Throwable) { null }
        return profession?.let {
            repository.getAllCompanies()
                .associate { it.name to it.vacancies }[companyName]
                ?.find { it.profession == profession }
        }
    }
}