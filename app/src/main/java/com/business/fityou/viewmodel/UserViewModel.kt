package com.business.fityou.viewmodel

import android.app.Activity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.business.fityou.data.models.User
import com.business.fityou.data.models.states.AuthState
import com.business.fityou.domain.UserRepository
import com.business.fityou.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    var signUpState by mutableStateOf(AuthState())
        private set

    // This is basically auth.currentUser. It also gets the data if user already
    //      signed in on startup.
    var signInState by mutableStateOf(AuthState())
        private set

    var user by mutableStateOf(User())
        private set

    fun signUpUser(
        userName: String,
        userEmail: String,
        userPassword: String,
        confirmPassword: String
    ) =
        viewModelScope.launch {

            val arePasswordMatching = userPassword == confirmPassword

            if (arePasswordMatching) {

                signUpState = signUpState.copy(
                    loading = true,
                    error = null,
                    success = false
                )

                val result = repository.createNewUser(
                    userName = userName,
                    userEmailAddress = userEmail,
                    userLoginPassword = userPassword
                )

                when (result) {
                    is Resource.Success -> {
                        signUpState = signUpState.copy(
                            data = result.data?.user,
                            loading = false,
                            success = true,
                            error = null
                        )
                    }

                    is Resource.Error -> {
                        signUpState = signUpState.copy(
                            data = null,
                            loading = false,
                            success = false,
                            error = result.message.toString()
                        )
                    }

                    is Resource.Loading -> {
                        signUpState = signUpState.copy(
                            data = null,
                            loading = true,
                            success = false,
                            error = null
                        )
                    }
                }
            } else {
                signUpState = signUpState.copy(
                    error = "passwords are not matching"
                )
            }

        }

    fun signInUser(userEmail: String, userPassword: String) =

        viewModelScope.launch {

            signInState = signInState.copy(
                loading = true
            )

            val result = repository.loginUser(email = userEmail, password = userPassword)

            signInState = when (result) {
                is Resource.Success -> {
                    signInState.copy(
                        loading = false,
                        success = true,
                        uid = result.data?.user?.uid
                    )

                }

                is Resource.Loading -> {
                    signInState.copy(
                        loading = true,
                        success = false,
                    )
                }

                is Resource.Error -> {
                    signInState.copy(
                        loading = false,
                        success = false,
                        error = result.message
                    )
                }
            }
        }

    fun launchGoogleSignIn(
        launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>
    ) {
        viewModelScope.launch {
            repository.getGoogleSignInIntent()?.let {
                launcher.launch( IntentSenderRequest.Builder(it).build() )
            }
        }
    }

    fun signInWithGoogle(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK && result.data != null)
            viewModelScope.launch {
                signInState = repository.loginUserByGoogle(result.data!!)
            }
    }

    fun checkLoginState() {
        repository.getCurrentUser()?.let {
            signInState = AuthState(
                success = true,
                uid = it.uid,
                data = it
            )
        }
    }


    fun logOut() {
        viewModelScope.launch {

            repository.logOutUser()

            signInState = AuthState()
            signUpState = AuthState()

        }
    }


}