package com.example.app.model

import com.example.app.util.DateSerializer
import com.example.app.util.YearMonthSerializer
import com.example.app.util.YearSerializer
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames
import java.time.Year
import java.time.YearMonth
import java.util.Date

@Serializable
data class Resume(
    var id: Int?,
    @SerialName("candidate_info") val candidateInfo: CandidateInfo,
    val education: List<Institution>,
    @SerialName("job_experience") val experience: List<Job>,
    @SerialName("free_form") val freeForm: String
)
//
//data class Institution(
//    val type: EducationType,
//    @SerialName("year_start") @Serializable(with = YearSerializer::class) val yearStart: Year,
//    @SerialName("year_end") @Serializable(with = YearSerializer::class) val yearEnd: Year,
//    val description: String
//)
//
//enum class EducationType(val value: String) {
//    @SerialName("higher") HIGH("higher"),
//    @SerialName("secondary special") SEC_SP("secondary special"),
//    @SerialName("secondary") SEC("secondary")
//}
//
//data class CandidateInfo(
//    val name: String,
//    val profession: Profession,
//    val sex: Sex,
//    @SerialName("birth_date") @Serializable(with = DateSerializer::class) val birthDate: Date,
//    val contacts: Contacts,
//    @SerialName("relocation") val relocation: RelocationWillingness
//)
//
//enum class Profession(val value: String) {
//    @SerializedName("developer") @SerialName("developer") DEV("Developer"),
//    @OptIn(ExperimentalSerializationApi::class)
//    @JsonNames("qa", "QA") QA("QA"),
//    @OptIn(ExperimentalSerializationApi::class)
//    @JsonNames("PM", "pm") PM("PM"),
//    @SerializedName("analyst") @SerialName("analyst") ANAL("Analyst"),
//    @SerializedName("designer") @SerialName("designer") DES("Designer");
//
//    companion object {
//        fun fromValue(value: String): Profession? {
//            return values().find { it.value == value }
//        }
//    }
//}
//
//enum class Sex(val value: String) {
//    @SerialName("male") MALE("male"),
//    @SerialName("female") FEM("female")
//}
//
//data class Contacts(
//    val phone: String,
//    val email: String
//)
//
//enum class RelocationWillingness(val value: String) {
//    @SerialName("preferable") PREFERABLE("preferable"),
//    @SerialName("possible") POSSIBLE("possible"),
//    @SerialName("impossible") IMPOSSIBLE("impossible")
//}
//
//data class Job(
//    @SerialName("date_start") @Serializable(with = YearMonthSerializer::class) val dateStart: YearMonth,
//    @SerialName("date_end") @Serializable(with = YearMonthSerializer::class) val dateEnd: YearMonth,
//    @SerialName("company_name") val companyName: String,
//    val description: String
//)