package ru.karapetiandav.itsurfer.model

data class Event(
    val title: String,
    val type: String? = null,
    val date: String? = null,
    val location: String? = null
)