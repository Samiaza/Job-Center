package org.example


import com.example.model.CandidateInfo
import com.example.model.Institution
import com.example.model.Job
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Resume(
    val id: Int?,
    @SerialName("candidate_info") val candidateInfo: CandidateInfo,
    val education: List<Institution>,
    @SerialName("job_experience") val experience: List<Job>,
    @SerialName("free_form") val freeForm: String
)

