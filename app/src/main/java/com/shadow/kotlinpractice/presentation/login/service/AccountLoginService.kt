package com.shadow.kotlinpractice.presentation.login.service

import android.accounts.Account
import android.accounts.AccountManager
import com.shadow.kotlinpractice.domain.model.LoggedInUser
import com.shadow.kotlinpractice.domain.repository.account.LoginService
import com.shadow.kotlinpractice.domain.repository.api.FakeLoginService
import io.reactivex.Single
import com.shadow.kotlinpractice.domain.repository.common.Result as ResponseResult

class AccountLoginService(private val accountManager: AccountManager, private val fakeLogin: FakeLoginService) : LoginService {
    companion object {
        const val TYPE = "Account"
    }

    override fun load(): Single<ResponseResult<LoggedInUser>> {
        if (accountManager.getAccountsByType(TYPE).isNullOrEmpty()) return Single.just(ResponseResult.Empty)
        return Single.just(accountManager.getAccountsByType(TYPE).first())
            .map { ResponseResult.Success(LoggedInUser(it.type, it.name)) }
    }

    override fun login(username: String, password: String): Single<ResponseResult<LoggedInUser>> {
        return fakeLogin.login(username, password)
            .map {
                Account(it.name, TYPE).also { account ->
                    val result = accountManager.addAccountExplicitly(account, password, null)
                    print(result)
                }
                ResponseResult.Success(LoggedInUser(it.token, it.name))
            }
    }
}