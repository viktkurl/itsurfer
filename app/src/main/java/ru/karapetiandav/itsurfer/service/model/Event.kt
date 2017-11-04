package ru.karapetiandav.itsurfer.service.model

data class Event(
    val title: String,
    val type: String? = null,
    val date: String? = null,
    val location: String? = null
)