package com.robertrussell.miguel.openweather.utils

import android.util.Patterns

object DataChecker {

    fun validateName(name: String): CheckerResult {
        return CheckerResult(
            (!name.isNullOrEmpty() && name.length >= 2)
        )
    }

    fun validateEmail(email: String): CheckerResult {
        return CheckerResult(
            !email.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        )
    }

    fun validateUsername(username: String): CheckerResult {
        return CheckerResult(
            (!username.isNullOrEmpty() && username.length >= 6)
        )
    }

    fun validatePassword(password: String): CheckerResult {
        // TODO: Can add rule for specific length and/or character combinations.
        return CheckerResult(
            (!password.isNullOrEmpty() && password.length >= 6)
        )
    }

    fun isUsernameNotEmpty(username: String): CheckerResult {
        return CheckerResult(!username.isNullOrEmpty())
    }

    fun isPasswordNotEmpty(password: String): CheckerResult {
        return CheckerResult(!password.isNullOrEmpty())
    }
}

data class CheckerResult(val status: Boolean = false)
