package me.dongheelee.androidtestssample

import java.util.regex.Pattern

class LoginPresenter(
    private val loginView: LoginContract.View,
    private val userRepository: UserRepository
) : LoginContract.Presenter {

    override fun onClickLogin() {
        val email = loginView.getEmailText()
        if (!isEmailValid(email)) {
            loginView.showMessageForInvalidEmail()
            return
        }

        val password = loginView.getPasswordText()
        if (!isPasswordValid(password)) {
            loginView.showMessageForInvalidPassword()
            return
        }

        loginView.hideKeyboard()
        loginView.showLoadingProgress()

        userRepository.login(email, password, object : UserRepositoryImpl.LoginResultCallback {
            override fun onSuccess() {
                loginView.hideLoadingProgress()
                loginView.showPopupForLoginSuccess()
            }

            override fun onFailure() {
                loginView.hideLoadingProgress()
                loginView.showMessageForLoginFailur()
            }
        })
    }

    private fun isEmailValid(email: String): Boolean {
        val patternString = "^[A-Z0-9a-z._%+-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,}$"
        val pattern = Pattern.compile(patternString)
        return pattern.matcher(email).matches()
    }

    private fun isPasswordValid(password: String) = password.length >= 8
}
