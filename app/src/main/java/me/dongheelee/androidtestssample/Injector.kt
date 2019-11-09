package me.dongheelee.androidtestssample

object Injector {

    fun provideLoginRepository() = UserRepositoryImpl()
}
