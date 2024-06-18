package com.zenithapps.mobilestack.repository.dto

import com.zenithapps.mobilestack.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,
    val email: String?,
    val marketingConsent: Boolean
)

fun UserDto.toModel(): User = User(
    id = id,
    email = email,
    marketingConsent = marketingConsent
)

fun User.toDto(): UserDto = UserDto(
    id = id,
    email = email,
    marketingConsent = marketingConsent
)
