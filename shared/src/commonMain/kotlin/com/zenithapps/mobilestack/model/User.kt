package com.zenithapps.mobilestack.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val email: String?,
    val marketingConsent: Boolean
)