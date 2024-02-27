package com.example.repository

import org.example.Company

interface CompanyRepository {
    fun createCompany(company: Company): Company
    fun getCompany(id: Int): Company?
    fun getAllCompanies(): List<Company>
    fun updateCompany(company: Company): Company?
    fun deleteCompany(id: Int): Company?
}