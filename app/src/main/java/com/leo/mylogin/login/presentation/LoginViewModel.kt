package com.leo.mylogin.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.leo.mylogin.login.data.LoginRepository
import com.leo.mylogin.login.data.utils.Output
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _userState = MutableStateFlow<Output<FirebaseUser>?>(null)
    val userState: StateFlow<Output<FirebaseUser>?> = _userState

    init {
        if (loginRepository.currentUser != null) {
            _userState.value = Output.Success(loginRepository.currentUser!!)
        }
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        val result = loginRepository.login(email, password)
        _userState.value = result
    }

    fun signUp(email: String, password: String) = viewModelScope.launch {
        val result = loginRepository.signUp(email, password)
        _userState.value = result
    }

    fun logOut() {
        loginRepository.logOut()
    }
}