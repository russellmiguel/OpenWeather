package com.robertrussell.miguel.openweather.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.robertrussell.miguel.openweather.model.SignInUIEvents
import com.robertrussell.miguel.openweather.model.SignInValues

class SignViewModel : ViewModel() {

    private val TAG = SignViewModel::class.simpleName

    val signInUIValue = mutableStateOf(SignInValues())
    var loginInProgress = mutableStateOf(false)

    fun onSignInEvent(event: SignInUIEvents) {
        when (event) {
            is SignInUIEvents.UsernameChanged -> {
                signInUIValue.value = signInUIValue.value.copy(
                    userName = event.user
                )
            }
            is SignInUIEvents.PasswordChanged -> {
                signInUIValue.value = signInUIValue.value.copy(
                    password = event.pass
                )
            }
            is SignInUIEvents.SignInButtonClicked -> {
                signIn()
            }
        }
    }

    private fun signIn() {
        loginInProgress.value = true
        val user = signInUIValue.value.userName
        val password = signInUIValue.value.password

        //TODO: Local db call, check credentials
        Log.d(TAG, "SignIn credentials : Username: $user , Password: $password")
    }
}