package com.zenithapps.mobilestack.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.zenithapps.mobilestack.component.RootComponent
import com.zenithapps.mobilestack.component.RootComponent.Child
import com.zenithapps.mobilestack.ui.style.MobileStackTheme

@Composable
fun RootView(component: RootComponent) {
    MobileStackTheme {
        Surface {
            Box {
                Children(
                    modifier = Modifier.fillMaxSize(),
                    stack = component.stack
                ) {
                    when (val child = it.instance) {
                        is Child.Profile -> {
                            ProfileScreen(child.component)
                        }

                        is Child.Loading -> {
                            LoadingScreen(child.component)
                        }

                        is Child.Welcome -> {
                            WelcomeScreen(child.component)
                        }
                    }
                }
            }
        }
    }
}