package com.zenithapps.mobilestack.provider

interface OSCapabilityProvider {
    fun openUrl(url: String)

    fun getPlatform(): Platform

    fun getAppVersion(): String

    fun managePurchases()

    enum class Platform {
        ANDROID,
        IOS
    }
}