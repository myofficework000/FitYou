package com.business.fityou.data.repository

import android.app.Application
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.business.fityou.data.models.User
import com.business.fityou.data.models.states.AuthState
import com.business.fityou.domain.GoogleAuthClient
import com.business.fityou.domain.UserRepository
import com.business.fityou.util.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val googleAuthClient: GoogleAuthClient,
    private val auth: FirebaseAuth
) : UserRepository {

    private val fireStoreUserCollection = Firebase.firestore.collection("users")

    override suspend fun createNewUser(
        userName: String,
        userEmailAddress: String,
        userLoginPassword: String
    ): Resource<AuthResult> {

        return try {

            val registrationResult =
                auth.createUserWithEmailAndPassword(userEmailAddress, userLoginPassword)
                    .await()

            val userId = registrationResult.user?.uid!!
            val newUser = User(
                userName = userName,
                userEmail = userEmailAddress
            )
            fireStoreUserCollection.document(userId).set(newUser).await()

            Resource.Success(registrationResult)

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message.toString())
        }


    }

    override suspend fun loginUser(email: String, password: String): Resource<AuthResult> {

        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Log.e("login", "logged in user ${result.user?.uid}")
            Resource.Success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message.toString())
        }


    }

    override suspend fun getGoogleSignInIntent(): IntentSender? =
        googleAuthClient.googleAuthSignIn()

    override suspend fun loginUserByGoogle(intent: Intent): AuthState =
        googleAuthClient.firebaseSignIn(intent)

    override suspend fun logOutUser() {
        googleAuthClient.signOut()
        //auth.signOut() // GoogleAuthClient.signOut() already does this.
    }

    override fun getCurrentUser(): FirebaseUser? = auth.currentUser
}