package com.example.app.model

import com.example.app.util.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class CandidateInfo(
    val name: String,
    val profession: Profession,
    val sex: Sex,
    @SerialName("birth_date") @Serializable(with = DateSerializer::class) val birthDate: Date,
    val contacts: Contacts,
    @SerialName("relocation") val relocation: RelocationWillingness
)