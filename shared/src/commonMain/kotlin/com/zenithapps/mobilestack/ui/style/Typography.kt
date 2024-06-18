package com.zenithapps.mobilestack.ui.style

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.zenithapps.mobilestack.resources.Poppins_Bold
import com.zenithapps.mobilestack.resources.Poppins_Medium
import com.zenithapps.mobilestack.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
private fun getFontFamily() = FontFamily(
    // TODO: add your font .ttf files to the composeResources/font folder and replace the font names below
    Font(resource = Res.font.Poppins_Medium, weight = FontWeight.Normal),
    Font(resource = Res.font.Poppins_Bold, weight = FontWeight.Bold)
)

@Composable
internal fun getTypography() = Typography(
    displayLarge = MaterialTheme.typography.displayLarge.copy(fontFamily = getFontFamily()),
    displayMedium = MaterialTheme.typography.displayMedium.copy(fontFamily = getFontFamily()),
    displaySmall = MaterialTheme.typography.displaySmall.copy(fontFamily = getFontFamily()),
    headlineLarge = MaterialTheme.typography.headlineLarge.copy(fontFamily = getFontFamily()),
    headlineMedium = MaterialTheme.typography.headlineMedium.copy(fontFamily = getFontFamily()),
    headlineSmall = MaterialTheme.typography.headlineSmall.copy(fontFamily = getFontFamily()),
    titleLarge = MaterialTheme.typography.titleLarge.copy(fontFamily = getFontFamily()),
    titleMedium = MaterialTheme.typography.titleMedium.copy(fontFamily = getFontFamily()),
    titleSmall = MaterialTheme.typography.titleSmall.copy(fontFamily = getFontFamily()),
    bodyLarge = MaterialTheme.typography.bodyLarge.copy(fontFamily = getFontFamily()),
    bodyMedium = MaterialTheme.typography.bodyMedium.copy(fontFamily = getFontFamily()),
    bodySmall = MaterialTheme.typography.bodySmall.copy(fontFamily = getFontFamily()),
    labelLarge = MaterialTheme.typography.labelLarge.copy(fontFamily = getFontFamily()),
    labelMedium = MaterialTheme.typography.labelMedium.copy(fontFamily = getFontFamily()),
    labelSmall = MaterialTheme.typography.labelSmall.copy(fontFamily = getFontFamily())
)