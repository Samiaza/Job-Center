package com.example.repository

import com.example.util.ResourceReader
import kotlinx.serialization.json.Json
import com.example.model.CompaniesList
import org.example.Resume

object ResumeRepositoryStubImpl: ResumeRepository {
    private val resumes: MutableList<Resume>
    init {
        val contentOfResume = ResourceReader
            .readResourceFile("resume.json")
        resumes = contentOfResume?.let {
            mutableListOf(Json.decodeFromString<Resume>(it))
        } ?: mutableListOf()
    }

    override fun createResume(resume: Resume): Resume {
        resumes.add(resume)
        return resume
    }

    override fun getResume(id: Int): Resume? {
        return resumes.find { it.id == id }
    }

    override fun getAllResumes(): List<Resume> {
        return resumes
    }

    override fun updateResume(resume: Resume): Resume? {
        val index = resumes.indexOfFirst { it.id == resume.id }
        return if (index != -1)  resume.also { resumes[index] = it } else null
    }

    override fun deleteResume(id: Int): Resume? {
        val resume = resumes.find { it.id == id }
        return resume?.also { resumes.remove(resume) }
    }
}