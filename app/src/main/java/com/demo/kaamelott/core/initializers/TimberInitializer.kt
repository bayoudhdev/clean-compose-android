package com.demo.kaamelott.core.initializers

import android.content.Context
import androidx.startup.Initializer
import com.demo.kaamelott.BuildConfig
import timber.log.Timber

class TimberInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            // TODO ADD CrashlyticsTree FOR RELEASE MODE
            //  Timber.plant(CrashlyticsTree())
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
