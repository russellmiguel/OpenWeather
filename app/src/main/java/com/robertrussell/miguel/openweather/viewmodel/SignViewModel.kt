package com.robertrussell.miguel.openweather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertrussell.miguel.openweather.model.SignInUIEvents
import com.robertrussell.miguel.openweather.model.SignInValues
import com.robertrussell.miguel.openweather.model.SignUpUIEvent
import com.robertrussell.miguel.openweather.model.SignUpValues
import com.robertrussell.miguel.openweather.model.api.Response
import com.robertrussell.miguel.openweather.model.dao.UserDao
import com.robertrussell.miguel.openweather.model.entity.User
import com.robertrussell.miguel.openweather.utils.DataChecker
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.lang.StringBuilder

class SignViewModel(private val userDao: UserDao) : ViewModel() {

    private val TAG = SignViewModel::class.simpleName

    private var signInUIValue = SignInValues()
    private var signInErrorMessage = StringBuilder()

    private var signUpUIValue = SignUpValues()
    private var signUpErrorMessage = StringBuilder()

    /**
     * Flow sign in values.
     */
    private val _result = MutableStateFlow<Response<User>>(Response.Success(null))
    val result: StateFlow<Response<User>> = _result

    /**
     * Flow sign up values.
     */
    private val _resultSignUp = MutableStateFlow<Response<User>>(Response.Success(null))
    val resultSignUp: StateFlow<Response<User>> = _resultSignUp

    /**
     * Add user to local db.
     * TODO: Move to repository class.
     */
    private fun insertUser(user: User): Flow<Response<User>> = flow {
        delay(1000)

        try {
            emit(Response.Loading)

            if (validateSignUpDetails(user)) {
                val result = userDao.insertUser(user)
                if (result.toInt() > 0) {
                    emit(Response.Success(user))
                } else {
                    emit(Response.Failure(throw Exception("Username is already existing.")))
                }
            } else {
                emit(Response.Failure(throw Exception(signUpErrorMessage.toString())))
            }
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }

    /**
     * Get certain user by username and password on local db.
     * TODO: Move to repository class.
     */
    private fun getUser(username: String, password: String): Flow<Response<User>> = flow {
        delay(1000)

        try {
            emit(Response.Loading)

            val result = userDao.getUser(username, password)
            if (result != null) {
                emit(Response.Success(result))
            } else {
                emit(Response.Failure(throw Exception("Invalid credentials")))
            }
        } catch (e: Exception) {
            emit(Response.Failure(e))
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

                /**
                 * Get credentials.
                 */
                val newUser = User(
                    name = signUpUIValue.name,
                    email = signUpUIValue.email,
                    username = signUpUIValue.userName,
                    password = signUpUIValue.password
                )

                viewModelScope.launch {
                    insertUser(newUser).collectLatest {
                        _resultSignUp.value = it
                    }
                }
            }
        }
    }

    private fun validateSignUpDetails(user: User): Boolean {
        if (!signUpErrorMessage.isNullOrEmpty()) {
            signUpErrorMessage.clear()
        }

        if (!DataChecker.validateName(user.name).status) {
            signUpErrorMessage.append("Name is empty or too short. \n")
            signUpUIValue.nameError = true
        } else {
            signUpUIValue.nameError = false
        }

        if (!DataChecker.validateEmail(user.email).status) {
            signUpErrorMessage.append("Invalid email. \n")
            signUpUIValue.emailError = true
        } else {
            signUpUIValue.emailError = false
        }

        if (!DataChecker.validateUsername(user.username).status) {
            signUpErrorMessage.append("Username is empty or too short, should be at least 6 characters. \n")
            signUpUIValue.userNameError = true
        } else {
            signUpUIValue.userNameError = false
        }

        if (!DataChecker.validatePassword(user.password).status) {
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
        _resultSignUp.value = Response.Success(null)
    }

    fun clearSignInValues() {
        signInErrorMessage.clear()
        signInUIValue = SignInValues()
        _result.value = Response.Success(null)
    }
}
