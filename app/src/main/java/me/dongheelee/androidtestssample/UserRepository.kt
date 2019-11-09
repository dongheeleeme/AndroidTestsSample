package me.dongheelee.androidtestssample

interface UserRepository {

    fun login(email: String, password: String, loginResultCallback: UserRepositoryImpl.LoginResultCallback)
}
