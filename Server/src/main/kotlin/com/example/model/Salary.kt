package com.example.model

enum class Salary(val range: IntRange) {
    LOW(0..salaryMiddleLimit){
        override val valueStr = "< $salaryMiddleLimit"
    },
    MID(salaryMiddleLimit..salaryHighLimit){
        override val valueStr = "$salaryMiddleLimit - $salaryHighLimit"
    },
    HIGH(salaryHighLimit..Int.MAX_VALUE){
        override val valueStr = "> $salaryHighLimit"
    };
    abstract val valueStr: String
}