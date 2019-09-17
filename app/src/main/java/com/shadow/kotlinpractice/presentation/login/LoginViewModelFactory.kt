package com.shadow.kotlinpractice.presentation.login

import android.accounts.AccountManager
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shadow.kotlinpractice.domain.repository.account.AccountRepository
import com.shadow.kotlinpractice.domain.repository.api.FakeLoginService
import com.shadow.kotlinpractice.presentation.login.service.AccountLoginService

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository = AccountRepository(loginService = AccountLoginService(AccountManager.get(context), object : FakeLoginService {}))
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
