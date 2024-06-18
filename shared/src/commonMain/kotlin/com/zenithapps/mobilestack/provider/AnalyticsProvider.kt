package com.zenithapps.mobilestack.provider

interface AnalyticsProvider {
    fun logEvent(eventName: String, screenName: String, params: Map<String, Any>)

}