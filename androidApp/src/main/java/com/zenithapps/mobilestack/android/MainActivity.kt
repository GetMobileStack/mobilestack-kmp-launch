package com.zenithapps.mobilestack.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.zenithapps.mobilestack.android.provider.AndroidCapabilityProvider
import com.zenithapps.mobilestack.android.provider.FirebaseAnalyticsProvider
import com.zenithapps.mobilestack.component.DefaultRootComponent
import com.zenithapps.mobilestack.ui.view.RootView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootComponent = DefaultRootComponent(
            componentContext = defaultComponentContext(),
            osCapabilityProvider = AndroidCapabilityProvider(this),
            analyticsProvider = FirebaseAnalyticsProvider(Firebase.analytics),
        )
        setContent {
            RootView(rootComponent)
        }
    }
}
