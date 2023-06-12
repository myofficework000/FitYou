package com.business.fityou.domain

import android.content.Intent
import android.content.IntentSender
import com.business.fityou.data.models.states.AuthState

interface GoogleAuthClient {
    suspend fun googleAuthSignIn(): IntentSender?
    suspend fun firebaseSignIn(intent: Intent): AuthState
    suspend fun signOut()
    fun getCurrentUser(): AuthState?
}