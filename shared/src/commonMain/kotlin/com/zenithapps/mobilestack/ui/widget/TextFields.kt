package com.zenithapps.mobilestack.ui.widget

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun MSOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    enabled: Boolean = true,
    shouldHide: Boolean = false,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) = OutlinedTextField(
    modifier = modifier,
    value = value,
    enabled = enabled,
    readOnly = readOnly,
    onValueChange = { onValueChange(it) },
    label = { Text(label) },
    singleLine = singleLine,
    shape = MaterialTheme.shapes.medium,
    visualTransformation = if (shouldHide) PasswordVisualTransformation() else VisualTransformation.None,
    keyboardOptions = keyboardOptions,
    keyboardActions = keyboardActions
)