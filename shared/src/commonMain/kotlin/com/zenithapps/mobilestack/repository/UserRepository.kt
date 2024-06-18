package com.zenithapps.mobilestack.repository

import com.zenithapps.mobilestack.model.User
import com.zenithapps.mobilestack.repository.dto.UserDto
import com.zenithapps.mobilestack.repository.dto.toDto
import com.zenithapps.mobilestack.repository.dto.toModel
import dev.gitlive.firebase.firestore.FirebaseFirestore
interface UserRepository {
    suspend fun createUser(
        id: String,
        email: String? = null,
        marketingConsent: Boolean? = null
    ): User

    suspend fun getUser(userId: String): User?
    suspend fun updateUser(user: User)
    suspend fun deleteUser(userId: String)
}

class FirebaseUserRepository(
    private val firebaseFirestore: FirebaseFirestore,
) : UserRepository {
    override suspend fun createUser(
        id: String,
        email: String?,
        marketingConsent: Boolean?
    ): User {
        val userDto = UserDto(
            id = id,
            email = email,
            marketingConsent = marketingConsent ?: false
        )
        firebaseFirestore.collection("users").document(userDto.id).set(userDto)
        return userDto.toModel()
    }

    override suspend fun getUser(userId: String): User? {
        val doc =
            firebaseFirestore.collection("users").document(userId).get()
        return if (doc.exists) {
            doc.data(UserDto.serializer()).toModel()
        } else {
            null
        }
    }

    override suspend fun updateUser(user: User) {
        val userDto = user.toDto()
        firebaseFirestore.collection("users").document(userDto.id).set(userDto)
    }

    override suspend fun deleteUser(userId: String) {
        firebaseFirestore.collection("users").document(userId).delete()
    }
}