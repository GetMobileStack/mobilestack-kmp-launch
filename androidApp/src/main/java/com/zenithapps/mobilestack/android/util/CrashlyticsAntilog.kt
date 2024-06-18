package com.zenithapps.mobilestack.android.util

import com.google.firebase.crashlytics.FirebaseCrashlytics
import io.github.aakira.napier.Antilog
import io.github.aakira.napier.LogLevel

class CrashlyticsAntilog : Antilog() {
    override fun performLog(
        priority: LogLevel,
        tag: String?,
        throwable: Throwable?,
        message: String?
    ) {
        // send only error log
        if (priority < LogLevel.ERROR) return

        throwable?.let {
            // Consider adding custom exception messages for certain exceptions eg. network exceptions (add body)
            FirebaseCrashlytics.getInstance().recordException(it)
        }
    }
}
