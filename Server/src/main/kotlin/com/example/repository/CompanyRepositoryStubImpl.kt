package com.example.repository

import com.example.util.ResourceReader
import kotlinx.serialization.json.Json
import com.example.model.CompaniesList
import org.example.Company

object CompanyRepositoryStubImpl: CompanyRepository {
    private val companies: MutableList<Company>
    init {
        val contentOfListOfCompanies = ResourceReader
            .readResourceFile("listOfCompanies.json")
        companies = contentOfListOfCompanies?.let {
            Json.decodeFromString<CompaniesList>(it)
                .companies
                .toMutableList()
        } ?: mutableListOf()
    }

    override fun createCompany(company: Company): Company {
        companies.add(company)
        return company
    }

    override fun getCompany(id: Int): Company? {
        return companies.find { it.id == id }
    }

    override fun getAllCompanies(): List<Company> {
        return companies
    }

    override fun updateCompany(company: Company): Company? {
        val index = companies.indexOfFirst { it.id == company.id }
        return if (index != -1)  company.also { companies[index] = it } else null
    }

    override fun deleteCompany(id: Int): Company? {
        val company = companies.find { it.id == id }
        return company?.also { companies.remove(company) }
    }
}