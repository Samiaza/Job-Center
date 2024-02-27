package com.example.app.network.api

import com.example.app.model.Vacancy
import com.example.app.model.Company
import com.example.app.model.Resume
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface VacancyService {
    @GET("companies")
    fun getCompanies(): Call<Map<String, String>>

    @GET("companies/id={id}")
    fun getCompany(@Path("id")id: Int): Call<Company>

    @GET("vacancies")
    fun getVacancies(): Call<Map<String, List<String>>>

    @GET("vacancies/details")
    fun getVacancy(@Query("company")company: String,
                   @Query("vacancy")vacancy: String)
    : Call<Vacancy>

    @GET("resumes/{id}")
    fun getResume(@Path("id")id: Int): Call<Resume>

    @POST("resumes")
    fun saveResume(@Body resume: Resume): Call<Resume>

    @GET("tags/{id}")
    fun getTags(@Path("id")id: Int): Call<List<String>>
}