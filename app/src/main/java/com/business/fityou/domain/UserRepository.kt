package com.business.fityou.domain

import com.google.firebase.auth.AuthResult
import com.business.fityou.util.Resource

interface UserRepository {

    suspend fun createNewUser(
        userName: String,
        userEmailAddress: String,
        userLoginPassword: String
    ): Resource<AuthResult>

    suspend fun loginUser(email: String, password: String): Resource<AuthResult>

    suspend fun logOutUser()
}