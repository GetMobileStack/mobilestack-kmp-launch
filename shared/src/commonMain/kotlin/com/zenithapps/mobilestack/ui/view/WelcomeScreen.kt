package com.zenithapps.mobilestack.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.zenithapps.mobilestack.component.WelcomeComponent
import com.zenithapps.mobilestack.resources.Res
import com.zenithapps.mobilestack.resources.img_app_icon
import com.zenithapps.mobilestack.ui.widget.FeatureItem
import com.zenithapps.mobilestack.ui.widget.MSFilledButton
import com.zenithapps.mobilestack.ui.widget.MSOutlinedButton
import org.jetbrains.compose.resources.painterResource

@Composable
fun WelcomeScreen(component: WelcomeComponent) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Image(
            painter = painterResource(Res.drawable.img_app_icon),
            contentDescription = "App Icon"
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = buildAnnotatedString {
                append("Ship your mobile app in days, ")
                withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                    append("not weeks")
                }
                append(".")
            },
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "The template with all you need to build your next SaaS.",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        FeatureItem("Shared UI")
        FeatureItem("DB + offline")
        FeatureItem("more...")
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No backend needed",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        MSFilledButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Get MobileStack",
            onClick = component::onPurchaseTap
        )
        MSOutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Explore the template",
            onClick = component::onSignUpTap
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}


