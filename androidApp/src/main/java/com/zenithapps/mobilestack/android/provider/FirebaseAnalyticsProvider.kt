package com.zenithapps.mobilestack.android.provider

import android.os.Bundle
import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import com.zenithapps.mobilestack.provider.AnalyticsProvider

class FirebaseAnalyticsProvider(
    private val firebaseAnalytics: FirebaseAnalytics
) : AnalyticsProvider {
    override fun logEvent(eventName: String, screenName: String, params: Map<String, Any>) {
        firebaseAnalytics.logEvent(
            eventName,
            params.toBundle().apply { putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName) })
    }

    private fun Map<String, Any?>.toBundle(): Bundle = bundleOf(*this.toList().toTypedArray())
}

