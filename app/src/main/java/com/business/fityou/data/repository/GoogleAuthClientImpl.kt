package com.business.fityou.data.repository

import android.content.Context
import android.content.Intent
import com.business.fityou.R
import com.business.fityou.data.models.states.AuthState
import com.business.fityou.domain.GoogleAuthClient
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GoogleAuthClientImpl @Inject constructor(
    private val signInClient: SignInClient,
    private val auth: FirebaseAuth
): GoogleAuthClient {
    override suspend fun googleAuthSignIn() =
        try {
            signInClient.beginSignIn(
                BeginSignInRequest.Builder()
                    .setGoogleIdTokenRequestOptions(
                        GoogleIdTokenRequestOptions.builder()
                            .setServerClientId(webClientId)
                            .setFilterByAuthorizedAccounts(false)
                            .setSupported(true)
                            .build()
                    )
                    .setAutoSelectEnabled(true)
                    .build()
            ).await()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } ?.pendingIntent?.intentSender

    override suspend fun firebaseSignIn(intent: Intent): AuthState =
        try {
            auth.signInWithCredential(
                GoogleAuthProvider.getCredential(
                    signInClient
                        .getSignInCredentialFromIntent(intent)
                        .googleIdToken,
                    null
                )
            ).await().user?.run{
                AuthState(
                    success = true,
                    uid = uid,
                    data = this
                )
                // Find a way to set error message in Res ID instead in the future.
                //      Code needed: R.string.google_sign_in_error1
            } ?: AuthState()
        } catch (e: Exception) {
            e.printStackTrace()
            AuthState(error = e.message)
        }

    override suspend fun signOut() {
        try {
            signInClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getCurrentUser() = auth.currentUser?.run {
        AuthState(
            success = true,
            uid = uid,
            data = this
        )
    }

    companion object {
        // While the web client secret MUST not be stored in the client,
        //     it is perfectly safe to do so for the web client ID.
        private const val webClientId =
            "839585157927-beb1grarfn9jq6nfoa6s5u02gsgpmfpo.apps.googleusercontent.com"
    }
}