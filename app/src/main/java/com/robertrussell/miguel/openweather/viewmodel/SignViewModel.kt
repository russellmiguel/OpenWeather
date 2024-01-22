package com.robertrussell.miguel.openweather.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertrussell.miguel.openweather.model.SignInUIEvents
import com.robertrussell.miguel.openweather.model.SignInValues
import com.robertrussell.miguel.openweather.model.SignResultDataClass
import com.robertrussell.miguel.openweather.model.SignUpUIEvent
import com.robertrussell.miguel.openweather.model.SignUpValues
import com.robertrussell.miguel.openweather.model.api.OpenWeatherDataModel
import com.robertrussell.miguel.openweather.model.api.Response
import com.robertrussell.miguel.openweather.model.dao.UserDao
import com.robertrussell.miguel.openweather.model.entity.User
import com.robertrussell.miguel.openweather.utils.DataChecker
import com.robertrussell.miguel.openweather.view.navigation.Navigation
import com.robertrussell.miguel.openweather.view.navigation.Pages
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.lang.StringBuilder

class SignViewModel(private val userDao: UserDao) : ViewModel() {

    private val TAG = SignViewModel::class.simpleName + "-Test"

    private var signInUIValue = SignInValues()
    private var signInErrorMessage = StringBuilder()

    private var signUpUIValue = SignUpValues()
    private var signUpInProgress = mutableStateOf(false)
    private var signUpErrorMessage = StringBuilder()

    var status = MutableLiveData<Boolean?>()

    /**
     * Flow sign in values.
     */
    private val _result = MutableStateFlow<Response<User>>(Response.Success(null))
    val result: StateFlow<Response<User>> = _result

    /**
     * Add user to local db.
     */
    private fun insertUser(user: User) {
        viewModelScope.launch {
            userDao.insertUser(user)
        }
    }

    /**
     * Get certain user by username and password on local db.
     * TODO: Moved to repository class.
     */
    private fun getUser(username: String, password: String): Flow<Response<User>> = flow {
        Log.d("SignViewModel", "Sign-trace getUser")
        delay(1000)

        try {
            emit(Response.Loading)

            val result = userDao.getUser(username, password)
            if (result != null) {
                emit(Response.Success(result))
            } else {
                emit(Response.Failure(throw Exception("Invalid credentials")))
            }

            Log.d("SignViewModel", "Sign-trace getUser Success: $result")
        } catch (e: Exception) {
            emit(Response.Failure(e))
            Log.d("SignViewModel", "Sign-trace getUser Failure: ${e.message}")
        }
    }

    fun onSignInEvent(event: SignInUIEvents) {
        when (event) {
            is SignInUIEvents.UsernameChanged -> {
                signInUIValue.userName = event.user
            }

            is SignInUIEvents.PasswordChanged -> {
                signInUIValue.password = event.pass
            }

            is SignInUIEvents.SignInButtonClicked -> {

                Log.d("SignViewModel", "Sign-trace SignInUIEvents.SignInButtonClicked")

                /**
                 * Get credentials.
                 */
                val user = signInUIValue.userName
                val password = signInUIValue.password

                /**
                 * Check credentials if existing on local db.
                 */
                viewModelScope.launch {
                    getUser(username = user, password = password).collectLatest {
                        _result.value = it
                    }
                }
            }
        }
    }

    fun onSignUpEvent(event: SignUpUIEvent) {
        when (event) {
            is SignUpUIEvent.NameChanged -> {
                signUpUIValue.name = event.name
            }

            is SignUpUIEvent.EmailChanged -> {
                signUpUIValue.email = event.email
            }

            is SignUpUIEvent.UsernameChanged -> {
                signUpUIValue.userName = event.user
            }

            is SignUpUIEvent.PasswordChanged -> {
                signUpUIValue.password = event.pass
            }

            is SignUpUIEvent.SignUpButtonClicked -> {
                Log.d(TAG, "SignUpUIEvent.SignUpButtonClicked!")
                signUpInProgress.value = true

                // validate credentials
                val valResult = validateSignUpDetails()
                Log.d(TAG, valResult.toString())

                // get credentials to save on local db
                val newUser = User(
                    name = signUpUIValue.name,
                    email = signUpUIValue.email,
                    username = signUpUIValue.userName,
                    password = signUpUIValue.password
                )

                if (valResult) {
                    status.value = true

                    // Insert new user
                    insertUser(newUser)
                } else {
                    status.value = false
                    signUpUIValue.errorMessage = signUpErrorMessage.toString()
                }
            }
        }
    }

    private fun validateSignUpDetails(): Boolean {
        if (!signUpErrorMessage.isNullOrEmpty()) {
            signUpErrorMessage.clear()
        }

        if (!DataChecker.validateName(signUpUIValue.name).status) {
            signUpErrorMessage.append("Name is empty or too short. \n")
            signUpUIValue.nameError = true
        } else {
            signUpUIValue.nameError = false
        }

        if (!DataChecker.validateEmail(signUpUIValue.email).status) {
            signUpErrorMessage.append("Invalid email. \n")
            signUpUIValue.emailError = true
        } else {
            signUpUIValue.emailError = false
        }

        if (!DataChecker.validateUsername(signUpUIValue.userName).status) {
            signUpErrorMessage.append("Username is empty or too short, should be at least 6 characters. \n")
            signUpUIValue.userNameError = true
        } else {
            signUpUIValue.userNameError = false
        }

        if (!DataChecker.validatePassword(signUpUIValue.password).status) {
            signUpErrorMessage.append("Password should be at least 6 characters. \n")
            signUpUIValue.passwordError = true
        } else {
            signUpUIValue.passwordError = false
        }

        return (!signUpUIValue.nameError && !signUpUIValue.emailError && !signUpUIValue.userNameError && !signUpUIValue.passwordError)
    }

    fun clearSignUpValues() {
        signUpErrorMessage.clear()
        signUpUIValue = SignUpValues()
        signUpInProgress.value = false
    }

    fun clearSignInValues() {
        signInErrorMessage.clear()
        signInUIValue = SignInValues()
        _result.value = Response.Success(null)
    }
}
