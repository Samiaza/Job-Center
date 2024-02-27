package com.example.service

import org.example.Resume
import com.example.repository.ResumeRepository

class ResumeService(private val repository: ResumeRepository) {
    private val tags: MutableMap<Int, Set<String>>
    init{
        tags = repository.getAllResumes()
            .filter {it.id != null}
            .associate { it.id!! to extractTags(it) }
            .toMutableMap()
    }

    fun getResumesList(): List<Resume> {
        return repository.getAllResumes()
    }

    fun getResume(id: Int): Resume? {
        return repository.getResume(id)
    }

    fun getTags(id: Int): List<String>? {
        return tags[id]?.toList()
    }

    companion object{
        fun extractTags(resume: Resume): Set<String> {
            var combinedText = resume.candidateInfo.profession.value + " "
            resume.experience.forEach { combinedText += (it.description + " ") }
            combinedText += resume.freeForm
            val words = combinedText.split(Regex("\\W+"))
            return words.mapNotNull { it.lowercase().takeIf { it.count() > 1 } }.toSet()
        }
    }
}