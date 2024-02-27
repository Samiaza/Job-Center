package com.example.plugins

import com.example.repository.CompanyRepositoryStubImpl
import com.example.repository.ResumeRepositoryStubImpl
import com.example.service.CompanyService
import com.example.service.ResumeService
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.example.Resume

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }

    val companyService = CompanyService(CompanyRepositoryStubImpl)
    val resumeService = ResumeService(ResumeRepositoryStubImpl)

    routing {
        get("/companies") {
            call.respond(companyService.getCompaniesList()
                .associate { "${it.name} (id = ${it.id})" to (it.activity?.value ?: "unknown") })
        }
    }

    routing {
        get("/companies/id={id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(id?.let { companyService.getCompany(id) }
                ?: HttpStatusCode.NotFound)
        }
    }

    routing {
        get("/vacancies") {
            call.respond(companyService.getVacancies())
        }
    }

    routing {
        get("/vacancies/details") {
            val companyName = call.parameters["company"] ?: "unknown"
            val vacancyName = call.parameters["vacancy"] ?: "unknown"
            call.respond(companyService.getVacancy(companyName, vacancyName)
                ?: HttpStatusCode.NotFound)
        }
    }

    routing {
        get("/resumes") {
            call.respond(resumeService.getResumesList()
                .associate { "id = ${it.id}" to it.candidateInfo.name })
        }
    }

    routing {
        get("/resumes/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(id?.let { resumeService.getResume(id) }
                ?: HttpStatusCode.NotFound)
        }
    }

    routing {
        post("/resumes") {
            val bodyText = call.receiveText()
            println(bodyText)
            val resume = Json.decodeFromString<Resume>(bodyText)
            println(resume)
            println(ResumeService.extractTags(resume))
            call.respond(HttpStatusCode.Created)
        }
    }

    routing {
        get("/tags/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(id?.let { resumeService.getTags(id) }
                ?: HttpStatusCode.NotFound)
        }
    }
}
