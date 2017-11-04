package ru.karapetiandav.itsurfer

import android.app.Application
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import ru.karapetiandav.itsurfer.service.repository.EventsService

class App : Application(), KodeinAware {
    override val kodein by Kodein.lazy {
        bind<EventsService>() with instance(EventsService())
    }
}