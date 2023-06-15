package com.business.fityou.data.models


// This is the schema that reflects the one on Firestore.
// If schema is changed on the console, remember to change them here as well.
data class User(
    val userEmail:String? = null,
    val userName:String? = null
)
