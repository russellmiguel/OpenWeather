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
import com.robertrussell.miguel.openweather.model.api.Response
import com.robertrussell.miguel.openweather.model.dao.UserDao
import com.robertrussell.miguel.openweather.model.entity.User
import com.robertrussell.miguel.openweather.utils.DataChecker
import com.robertrussell.miguel.openweather.view.navigation.Navigation
import com.robertrussell.miguel.openweather.view.navigation.Pages
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.lang.StringBuilder

class SignViewModel(private val userDao: UserDao) : ViewModel() {

    private val TAG = SignViewModel::class.simpleName + "-Test"

    private var signInUIValue = SignInValues()
    private var loginInProgress = mutableStateOf(false)
    private var signInErrorMessage = StringBuilder()

    private var signUpUIValue = SignUpValues()
    private var signUpInProgress = mutableStateOf(false)
    private var signUpErrorMessage = StringBuilder()

    var status = MutableLiveData<Boolean?>()

    /**
     * Observable sign up values.
     */
    //private var _newUserData = MutableLiveData<SignUpValues>(SignUpValues(""))
    //var newUserData: LiveData<SignUpValues> = _newUserData

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
     */
    private fun getUser(username: String, password: String): User {
        return userDao.getUser(username, password)
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
                loginInProgress.value = true

                val valResult = validateSignInDetails()
                if (valResult) {
                    val user = signInUIValue.userName
                    val password = signInUIValue.password

                    getUser(username = user, password = password).also {
                        Thread.sleep(1000L)
                        Log.d(TAG, "SignIn Current User: $it")
                        if (it != null) {
                            clearSignInValues()
                            Navigation.navigateTo(Pages.HomeScreen)
                        } else {
                            signInErrorMessage.clear()
                            signInErrorMessage.append("Invalid username and/or password.")
                        }
                    }
                } else {
                    signInErrorMessage.append("Username and/or Password is empty.")
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
                    insertUser(newUser).also {
                        Thread.sleep(1000L)
                        val newAddedUser = getUser(signUpUIValue.userName, signUpUIValue.password)
                        val _newUser = SignUpValues(
                            name = newAddedUser.name,
                            email = newAddedUser.email,
                            userName = newAddedUser.username,
                            password = newAddedUser.password,
                        )
                        //_newUserData.postValue(_newUser)

                        // Check new user
                        Log.d(TAG, newAddedUser.toString())
                        clearSignUpValues()
                    }
                } else {
                    status.value = false
                    signUpUIValue.errorMessage = signUpErrorMessage.toString()
                    //_newUserData.value = signUpUIValue
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

    private fun validateSignInDetails(): Boolean {
        if (!signInErrorMessage.isNullOrEmpty()) {
            signInErrorMessage.clear()
        }

        return !(signInUIValue.userName.isEmpty() && signInUIValue.password.isEmpty())
    }

    fun clearSignUpValues() {
        signUpErrorMessage.clear()
        signUpUIValue = SignUpValues()
        signUpInProgress.value = false
    }

    fun clearSignInValues() {
        signInErrorMessage.clear()
        signInUIValue = SignInValues()
        loginInProgress.value = false
    }
}