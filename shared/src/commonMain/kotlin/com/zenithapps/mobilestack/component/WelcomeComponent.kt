package com.zenithapps.mobilestack.component

import com.arkivanov.decompose.ComponentContext
import com.zenithapps.mobilestack.component.WelcomeComponent.Output
import com.zenithapps.mobilestack.provider.AnalyticsProvider

interface WelcomeComponent {
    fun onSignUpTap()
    fun onPurchaseTap()

    sealed interface Output {
        data object SignUp : Output
        data object Purchase : Output
    }
}

private const val SCREEN_NAME = "welcome"

class DefaultWelcomeComponent(
    componentContext: ComponentContext,
    private val analyticsProvider: AnalyticsProvider,
    private val onOutput: (Output) -> Unit
) : WelcomeComponent, ComponentContext by componentContext {
    override fun onSignUpTap() {
        analyticsProvider.logEvent(
            eventName = "sign_up_tap",
            screenName = SCREEN_NAME,
            params = emptyMap()
        )
        onOutput(Output.SignUp)
    }

    override fun onPurchaseTap() {
        analyticsProvider.logEvent(
            eventName = "purchase_tap",
            screenName = SCREEN_NAME,
            params = emptyMap()
        )
        onOutput(Output.Purchase)
    }
}