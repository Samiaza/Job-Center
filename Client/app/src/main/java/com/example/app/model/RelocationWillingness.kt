package com.example.app.model

import kotlinx.serialization.SerialName

enum class RelocationWillingness(val value: String) {
    @SerialName("preferable") PREFERABLE("preferable"),
    @SerialName("possible") POSSIBLE("possible"),
    @SerialName("impossible") IMPOSSIBLE("impossible")
}