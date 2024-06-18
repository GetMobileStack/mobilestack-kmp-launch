package com.zenithapps.mobilestack.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushToFront
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.zenithapps.mobilestack.component.RootComponent.Child
import com.zenithapps.mobilestack.provider.AnalyticsProvider
import com.zenithapps.mobilestack.provider.OSCapabilityProvider
import com.zenithapps.mobilestack.repository.FirebaseUserRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.serialization.Serializable

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class Loading(val component: LoadingComponent) : Child
        class Profile(val component: ProfileComponent) : Child
        class Welcome(val component: WelcomeComponent) : Child
    }
}

class DefaultRootComponent(
    componentContext: ComponentContext,
    private val osCapabilityProvider: OSCapabilityProvider,
    private val analyticsProvider: AnalyticsProvider,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Loading,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private val userRepository by lazy {
        FirebaseUserRepository(
            firebaseFirestore = Firebase.firestore
        )
    }

    init {
        setup()
    }

    // TIP: runs when app opens
    private fun setup() {
        // TODO: setup your app and load any configs before navigating to the first screen
        navigation.replaceAll(Config.Welcome)
    }

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): Child =
        when (config) {
            Config.Loading -> Child.Loading(
                component = DefaultLoadingComponent(componentContext)
            )


            is Config.Profile -> Child.Profile(
                component = DefaultProfileComponent(
                    componentContext = componentContext,
                    canGoBack = config.canGoBack,
                    userRepository = userRepository,
                    osCapabilityProvider = osCapabilityProvider,
                    analyticsProvider = analyticsProvider,
                    onOutput = { output ->
                        when (output) {
                            ProfileComponent.Output.Purchase -> {} // TODO: navigate to your paywall here
                            ProfileComponent.Output.GoBack -> navigation.pop()
                            ProfileComponent.Output.SignedOut -> navigation.replaceAll(Config.Welcome)
                        }
                    }
                )
            )

            Config.Welcome -> Child.Welcome(
                component = DefaultWelcomeComponent(
                    componentContext = componentContext,
                    analyticsProvider = analyticsProvider,
                    onOutput = { output ->
                        when (output) {
                            WelcomeComponent.Output.SignUp -> navigation.pushToFront(
                                Config.Profile(canGoBack = true)
                            ) // TODO: navigate to your sign up screen here
                            WelcomeComponent.Output.Purchase -> navigation.pushToFront(
                                Config.Profile(canGoBack = true)
                            ) // TODO: navigate to your paywall here
                        }
                    }
                )
            )
        }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Loading : Config

        @Serializable
        data class Profile(val canGoBack: Boolean = false) : Config

        @Serializable
        data object Welcome : Config
    }
}