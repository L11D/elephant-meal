package com.example.elephantmeal.day_screen.domain

import java.time.LocalDate

data class Mealtime(
    val name: String,
    val caloric: Int,
    val receipt: String,
    val startTime: String,
    val endTime: String,
    val dateTime: LocalDate
)
