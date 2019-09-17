package com.shadow.kotlinpractice.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.shadow.kotlinpractice.domain.repository.account.AccountRepository
import com.shadow.kotlinpractice.domain.repository.common.Result

import com.shadow.kotlinpractice.R
import com.shadow.kotlinpractice.domain.model.LoggedInUser

class LoginViewModel(private val loginRepository: AccountRepository) : ViewModel() {

    val isLogin: Boolean = loginRepository.isLoggedIn
    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        _loginResult.value = when (val result = loginRepository.login(username, password).blockingGet()) {
            is Result.Success<LoggedInUser> -> LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
            else -> LoginResult(error = R.string.login_failed)
        }
    }

    fun loginDataChanged(username: String, password: String) {
        _loginForm.value = when {
            !isUserNameValid(username) -> LoginFormState(usernameError = R.string.invalid_username)
            !isPasswordValid(password) -> LoginFormState(passwordError = R.string.invalid_password)
            else -> LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
