package com.leo.mylogin.login.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.leo.mylogin.login.data.utils.Output
import com.leo.mylogin.login.data.utils.parseResponse
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : LoginRepository {

    override val currentUser: FirebaseUser?
        get() = auth.currentUser

    override suspend fun login(email: String, password: String): Output<FirebaseUser> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).parseResponse()
            Output.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Output.Failure(e)
        }
    }

    override suspend fun signUp(email: String, password: String): Output<FirebaseUser> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).parseResponse()
            Output.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Output.Failure(e)
        }
    }

    override fun logOut() {
        auth.signOut()
    }
}