package com.example.app.model

import com.example.app.util.YearMonthSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.YearMonth

@Serializable
data class Job(
    @SerialName("date_start") @Serializable(with = YearMonthSerializer::class) val dateStart: YearMonth,
    @SerialName("date_end") @Serializable(with = YearMonthSerializer::class) val dateEnd: YearMonth,
    @SerialName("company_name") val companyName: String,
    val description: String
)