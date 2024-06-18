package com.zenithapps.mobilestack.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnResume
import com.zenithapps.mobilestack.component.ProfileComponent.Model
import com.zenithapps.mobilestack.component.ProfileComponent.Output
import com.zenithapps.mobilestack.model.User
import com.zenithapps.mobilestack.provider.AnalyticsProvider
import com.zenithapps.mobilestack.provider.OSCapabilityProvider
import com.zenithapps.mobilestack.repository.UserRepository
import com.zenithapps.mobilestack.util.createCoroutineScope
import kotlinx.coroutines.launch

interface ProfileComponent {
    val model: Value<Model>

    data class Model(
        val loading: Boolean = false,
        val user: User? = null,
        val appVersion: String = "",
        val newEmail: String = "",
        val editModeEnabled: Boolean = false,
        val isAnonymous: Boolean = false,
        val canGoBack: Boolean,
        val error: String? = "Network failed",
    )

    fun onSignOutTap()

    fun onPurchaseTap()

    fun onMarketingConsentChanged(consent: Boolean)

    fun onManagePurchasesTap()

    fun onRestorePurchasesTap()

    fun onHelpTap()

    fun onPrivacyPolicyTap()

    fun onTermsOfServiceTap()

    fun onOpenSourceLibrariesTap()

    fun onEmailChanged(email: String)

    fun onSaveEmailTap()

    fun onDeleteAccountTap()

    fun onEnableEditModeTap()

    fun onBackTap()

    sealed interface Output {
        data object Purchase : Output
        data object SignedOut : Output
        data object GoBack : Output
    }
}

private const val SCREEN_NAME = "profile"

class DefaultProfileComponent(
    componentContext: ComponentContext,
    canGoBack: Boolean,
    private val userRepository: UserRepository,
    private val osCapabilityProvider: OSCapabilityProvider,
    private val analyticsProvider: AnalyticsProvider,
    private val onOutput: (Output) -> Unit
) : ProfileComponent, ComponentContext by componentContext {
    override val model = MutableValue(Model(canGoBack = canGoBack))

    private val scope = createCoroutineScope()

    init {
        lifecycle.doOnResume {
            model.value = model.value.copy(loading = true)
            scope.launch {
                try {
                    val user = userRepository.getUser("0")
                        ?: userRepository.createUser("0") // TODO: replace 0 with your auth ids
                    model.value = model.value.copy(
                        user = user,
                        newEmail = user.email ?: "",
                        isAnonymous = true,
                        error = null
                    )
                } catch (e: Exception) {
                    val message = if (e.message?.contains("PERMISSION_DENIED") == true) {
                        "Permission denied. Make sure you finished setting up your project using MobileStack documentation."
                    } else {
                        e.message ?: "An error occurred"
                    }
                    model.value = model.value.copy(error = message)
                } finally {
                    model.value = model.value.copy(
                        loading = false,
                        appVersion = osCapabilityProvider.getAppVersion(),
                    )
                }
            }
        }
    }

    override fun onSignOutTap() {
        analyticsProvider.logEvent(
            eventName = "sign_out_tap",
            screenName = SCREEN_NAME,
            params = emptyMap()
        )
        model.value = model.value.copy(loading = true)
        // TODO: perform your auth sign out
        onOutput(Output.SignedOut)
    }

    override fun onPurchaseTap() {
        analyticsProvider.logEvent(
            eventName = "purchase_tap",
            screenName = SCREEN_NAME,
            params = emptyMap()
        )
        onOutput(Output.Purchase)
    }

    override fun onMarketingConsentChanged(consent: Boolean) {
        analyticsProvider.logEvent(
            eventName = "marketing_consent_changed",
            screenName = SCREEN_NAME,
            params = mapOf("consent" to consent)
        )
        scope.launch {
            try {
                val user = model.value.user ?: return@launch
                model.value = model.value.copy(loading = true)
                userRepository.updateUser(user.copy(marketingConsent = consent))
                model.value =
                    model.value.copy(user = user.copy(marketingConsent = consent), loading = false)
            } catch (e: Exception) {
                model.value =
                    model.value.copy(loading = false, error = e.message ?: "An error occurred")
            }

        }
    }

    override fun onManagePurchasesTap() {
        analyticsProvider.logEvent(
            eventName = "manage_purchases_tap",
            screenName = SCREEN_NAME,
            params = emptyMap()
        )
        osCapabilityProvider.managePurchases()
    }

    override fun onRestorePurchasesTap() {
        analyticsProvider.logEvent(
            eventName = "restore_purchases_tap",
            screenName = SCREEN_NAME,
            params = emptyMap()
        )
        // TODO: implement restore purchases
    }

    override fun onHelpTap() {
        analyticsProvider.logEvent(
            eventName = "help_tap",
            screenName = SCREEN_NAME,
            params = emptyMap()
        )
        osCapabilityProvider.openUrl("mailto:support@YOUR_DOMAIN.com") // TODO: replace with your support email
    }

    override fun onPrivacyPolicyTap() {
        analyticsProvider.logEvent(
            eventName = "privacy_policy_tap",
            screenName = SCREEN_NAME,
            params = emptyMap()
        )
        osCapabilityProvider.openUrl("https://getmobilestack.com") // TODO: replace with your privacy policy url
    }

    override fun onTermsOfServiceTap() {
        analyticsProvider.logEvent(
            eventName = "terms_of_service_tap",
            screenName = SCREEN_NAME,
            params = emptyMap()
        )
        osCapabilityProvider.openUrl("https://getmobilestack.com") // TODO: replace with your terms of service url
    }

    override fun onOpenSourceLibrariesTap() {
        analyticsProvider.logEvent(
            eventName = "open_source_libraries_tap",
            screenName = SCREEN_NAME,
            params = emptyMap()
        )
        osCapabilityProvider.openUrl("https://getmobilestack.com") // TODO: replace with your open source libraries url
    }

    override fun onEmailChanged(email: String) {
        model.value = model.value.copy(newEmail = email)
    }

    override fun onSaveEmailTap() {
        val newEmail = model.value.newEmail
        analyticsProvider.logEvent(
            eventName = "save_email_tap",
            screenName = SCREEN_NAME,
            params = emptyMap()
        )
        if (newEmail.isEmpty()) {
            model.value = model.value.copy(
                error = "Email cannot be empty"
            )
            return
        }
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@(.+)\$")
        if (!emailRegex.matches(newEmail)) {
            model.value = model.value.copy(
                error = "Invalid email"
            )
            return
        }
        model.value = model.value.copy(loading = true, error = null)
        scope.launch {
            try {
                val user = model.value.user ?: return@launch
                userRepository.updateUser(user.copy(email = newEmail))
                model.value = model.value.copy(
                    user = user.copy(email = newEmail),
                    loading = false,
                    editModeEnabled = false,
                    error = null
                )
            } catch (e: Exception) {
                model.value = model.value.copy(
                    loading = false,
                    error = e.message ?: "An error occurred"
                )
            }
        }
    }

    override fun onDeleteAccountTap() {
        analyticsProvider.logEvent(
            eventName = "delete_account_tap",
            screenName = SCREEN_NAME,
            params = emptyMap()
        )
        model.value = model.value.copy(loading = true)
        scope.launch {
            try {
                userRepository.deleteUser(model.value.user!!.id)
                // TODO: perform your auth delete account
                onOutput(Output.SignedOut)
            } catch (e: Exception) {
                model.value =
                    model.value.copy(loading = false, error = e.message ?: "An error occurred")
            }
        }
    }

    override fun onEnableEditModeTap() {
        analyticsProvider.logEvent(
            eventName = "enable_edit_mode_tap",
            screenName = SCREEN_NAME,
            params = emptyMap()
        )
        model.value = model.value.copy(editModeEnabled = true)
    }

    override fun onBackTap() {
        onOutput(Output.GoBack)
    }
}