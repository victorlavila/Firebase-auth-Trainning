package com.leo.mylogin.login.data

import com.google.firebase.auth.FirebaseUser
import com.leo.mylogin.login.data.utils.Output

interface LoginRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): Output<FirebaseUser>
    suspend fun signUp(email: String, password: String): Output<FirebaseUser>
    fun logOut()
}