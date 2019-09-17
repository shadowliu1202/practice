package com.shadow.kotlinpractice.domain.repository.account

import com.shadow.kotlinpractice.domain.repository.common.Result
import com.shadow.kotlinpractice.domain.model.LoggedInUser
import io.reactivex.Single
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
interface LoginService {

    fun load(): Single<Result<LoggedInUser>>

    fun login(username: String, password: String): Single<Result<LoggedInUser>> {
        try {
            // TODO: handle loggedInUser authentication
            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
            return Single.just(Result.Success(fakeUser))
        } catch (e: Throwable) {
            return Single.just(Result.Error(IOException("Error logging in", e) as Exception))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }


}

