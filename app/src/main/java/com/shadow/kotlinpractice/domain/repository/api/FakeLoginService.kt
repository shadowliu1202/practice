package com.shadow.kotlinpractice.domain.repository.api

import io.reactivex.Single

data class FakerUser(val name: String, val password: String, val token: String)
interface FakeLoginService {
    fun login(name: String, password: String): Single<FakerUser> {
        return Single.just(FakerUser(name, password, "test"))
    }
}