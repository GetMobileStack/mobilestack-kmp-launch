package com.zenithapps.mobilestack.android

import android.app.Application
import com.zenithapps.mobilestack.android.util.CrashlyticsAntilog
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class MobileStackApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Napier.base(DebugAntilog())
        } else {
            Napier.base(CrashlyticsAntilog())
        }
    }
}