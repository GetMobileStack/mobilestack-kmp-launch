package com.zenithapps.mobilestack

import androidx.compose.ui.window.ComposeUIViewController
import com.zenithapps.mobilestack.component.RootComponent
import com.zenithapps.mobilestack.ui.view.RootView

fun createRootViewController(component: RootComponent) =
    ComposeUIViewController { RootView(component = component) }