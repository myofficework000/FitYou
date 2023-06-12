package com.business.fityou.data.models.states

import com.google.firebase.auth.FirebaseUser

data class AuthState(
    val data: FirebaseUser? = null,
    val loading: Boolean = false,
    val success:Boolean = false,
    val error: String? = null,
    val uid:String? = null
)
