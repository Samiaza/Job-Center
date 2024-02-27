package com.example.app.model

import com.example.app.util.YearSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Year

@Serializable
data class Institution(
    val type: EducationType,
    @SerialName("year_start") @Serializable(with = YearSerializer::class) val yearStart: Year,
    @SerialName("year_end") @Serializable(with = YearSerializer::class) val yearEnd: Year,
    val description: String
)