package ru.karapetiandav.itsurfer.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.provider
import ru.karapetiandav.itsurfer.service.repository.EventsService

val kodein = Kodein {
    bind<EventsService>() with provider { EventsService() }
}