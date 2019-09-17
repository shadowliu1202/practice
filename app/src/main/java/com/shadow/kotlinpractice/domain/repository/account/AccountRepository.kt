package com.shadow.kotlinpractice.domain.repository.account

import com.shadow.kotlinpractice.domain.repository.common.Result
import com.shadow.kotlinpractice.domain.model.LoggedInUser
import io.reactivex.Single
import io.reactivex.functions.Predicate

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class AccountRepository(val loginService: LoginService) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = when (val result = loginService.load().blockingGet()) {
            is Result.Success -> result.data
            else -> null
        }
    }

    fun logout() {
        user = null
        loginService.logout()
    }

    fun login(username: String, password: String): Single<Result<LoggedInUser>> {
        return loginService.login(username, password)
            .doAfterSuccess { if (it is Result.Success<*>) setLoggedInUser(it.data as LoggedInUser) }
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}
