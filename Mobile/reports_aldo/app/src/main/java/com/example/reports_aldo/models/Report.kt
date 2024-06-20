package com.example.reports_aldo.models

data class Report(
    val imageUrl: String,
    val description: String,
    val urgency: String,
    val timestamp: Long,
    val status: String = "not done"  // Add status field with default value
)

