package me.dongheelee.androidtestssample

import kotlin.concurrent.thread

class UserRepositoryImpl : UserRepository {

    override fun login(email: String, password: String, loginResultCallback: LoginResultCallback) {
        println("$email / $password")
        thread {
            Thread.sleep(3000L)
            if (email == "tester@google.com") {
                loginResultCallback.onSuccess()
            } else {
                loginResultCallback.onFailure()
            }
        }
    }

    interface LoginResultCallback {

        fun onSuccess()
        fun onFailure()
    }
}
