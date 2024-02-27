package com.example.repository

import org.example.Resume

interface ResumeRepository {
    fun createResume(resume: Resume): Resume
    fun getResume(id: Int): Resume?
    fun getAllResumes(): List<Resume>
    fun updateResume(resume: Resume): Resume?
    fun deleteResume(id: Int): Resume?
}