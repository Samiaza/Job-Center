package com.example.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.time.Year
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Date

object DateSerializer : KSerializer<Date> {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy")
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Date" , PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Date = dateFormat.parse(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: Date) = encoder.encodeString(dateFormat.format(value))
}

object YearSerializer : KSerializer<Year> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Year" , PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Year = Year.of(decoder.decodeString().toInt())

    override fun serialize(encoder: Encoder, value: Year) = encoder.encodeString(value.toString())
}

object YearMonthSerializer : KSerializer<YearMonth> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("YearMonth" , PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): YearMonth = YearMonth.parse(decoder.decodeString(), DateTimeFormatter.ofPattern("MM.yyyy"))

    override fun serialize(encoder: Encoder, value: YearMonth) = encoder.encodeString(value.toString())
}