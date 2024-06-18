package com.zenithapps.mobilestack.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.zenithapps.mobilestack.component.ProfileComponent
import com.zenithapps.mobilestack.ui.widget.MSFilledButton
import com.zenithapps.mobilestack.ui.widget.MSOutlinedButton
import com.zenithapps.mobilestack.ui.widget.MSOutlinedTextField
import com.zenithapps.mobilestack.ui.widget.MSTopAppBar

@Composable
fun ProfileScreen(component: ProfileComponent) {
    val model by component.model.subscribeAsState()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    Scaffold(
        topBar = {
            MSTopAppBar(
                title = "Profile",
                onBackTap = if (model.canGoBack) component::onBackTap else null
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it).padding(16.dp)
        ) {
            item {
                if (model.error != null) {
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = "Error: ${model.error}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                Text(
                    text = "Hi, ${model.user?.email?.split("@")?.getOrNull(0) ?: "Guest"}",
                    style = MaterialTheme.typography.displayMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(8.dp))
            }
            item {
                SettingsCard(
                    title = "Account"
                ) {
                    if (model.editModeEnabled.not()) {
                        SettingsTextItem(
                            label = "Email",
                            value = model.user?.email ?: "Add Email",
                            onClick = if (model.user?.email.isNullOrEmpty()) component::onEnableEditModeTap else {
                                {}
                            }
                        )
                    } else {
                        SettingsTextFieldItem(
                            label = "Email",
                            value = model.newEmail,
                            onValueChanged = component::onEmailChanged,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Email,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(onNext = {
                                focusManager.clearFocus()
                                keyboardController?.hide()
                            })
                        )
                        if (model.newEmail.isNotBlank() && model.newEmail != model.user?.email) {
                            Spacer(Modifier.height(8.dp))
                            MSFilledButton(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Save Email",
                                onClick = component::onSaveEmailTap,
                                loading = model.loading
                            )
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    SettingsSwitchItem(
                        label = "Marketing consent",
                        value = model.user?.marketingConsent ?: false,
                        onValueChanged = component::onMarketingConsentChanged
                    )
                    if (!model.isAnonymous) {
                        Spacer(Modifier.height(8.dp))
                        MSOutlinedButton(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Sign Out",
                            colors = ButtonDefaults.outlinedButtonColors().copy(
                                contentColor = MaterialTheme.colorScheme.error
                            ),
                            onClick = component::onSignOutTap,
                            loading = model.loading,
                        )
                    }
                }
            }
            item {
                SettingsCard(
                    title = "Billing"
                ) {
                    SettingsTextItem(
                        label = "Purchases",
                        value = "None yet", // TODO: show user's purchases here according to entitlements
                        onClick = component::onPurchaseTap
                    )
                    Spacer(Modifier.height(8.dp))
                    SettingsNavigationItem(
                        label = "Manage Subscriptions/Purchases",
                        onClick = component::onManagePurchasesTap
                    )
                    Spacer(Modifier.height(8.dp))
                    MSOutlinedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Restore Purchases",
                        loading = model.loading,
                        onClick = component::onRestorePurchasesTap
                    )
                }
            }
            item {
                SettingsCard(
                    title = "About"
                ) {
                    SettingsTextItem(
                        label = "Version",
                        value = model.appVersion
                    )
                    Spacer(Modifier.height(8.dp))
                    SettingsNavigationItem(
                        label = "Help",
                        onClick = component::onHelpTap
                    )
                    Spacer(Modifier.height(8.dp))
                    SettingsNavigationItem(
                        label = "Privacy Policy",
                        onClick = component::onPrivacyPolicyTap
                    )
                    Spacer(Modifier.height(8.dp))
                    SettingsNavigationItem(
                        label = "Terms of Service",
                        onClick = component::onTermsOfServiceTap
                    )
                    Spacer(Modifier.height(8.dp))
                    SettingsNavigationItem(
                        label = "Open Source Libraries",
                        onClick = component::onOpenSourceLibrariesTap
                    )
                }
            }
            item {
                MSFilledButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Delete Account / Data",
                    colors = ButtonDefaults.buttonColors().copy(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    loading = model.loading,
                    onClick = component::onDeleteAccountTap
                )
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun SettingsCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium
    )
    Spacer(Modifier.height(8.dp))
    Card {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            content()
        }
    }
    Spacer(Modifier.height(16.dp))
}


@Composable
fun SettingsTextItem(
    label: String,
    value: String,
    onClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Spacer(modifier = Modifier.weight(1f))
        TextButton(
            onClick = onClick
        ) {
            Text(
                text = value,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

@Composable
fun SettingsTextFieldItem(
    label: String,
    value: String,
    onValueChanged: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.width(24.dp))
        MSOutlinedTextField(
            value = value,
            onValueChange = onValueChanged,
            label = "",
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions
        )
    }
}

@Composable
fun SettingsSwitchItem(
    label: String,
    value: Boolean,
    onValueChanged: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = value,
            onCheckedChange = onValueChanged
        )
    }
}

@Composable
fun SettingsNavigationItem(
    label: String,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = colors.contentColor
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            colors = colors,
            onClick = onClick
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Navigation Arrow"
            )
        }
    }
}