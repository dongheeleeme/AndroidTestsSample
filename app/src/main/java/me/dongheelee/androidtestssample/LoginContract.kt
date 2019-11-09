package me.dongheelee.androidtestssample

interface LoginContract {

    interface View {
        fun getEmailText(): String
        fun showMessageForInvalidEmail()
        fun getPasswordText(): String
        fun showMessageForInvalidPassword()
        fun showLoadingProgress()
        fun hideLoadingProgress()
        fun showPopupForLoginSuccess()
        fun hideKeyboard()
        fun showMessageForLoginFailur()
    }

    interface Presenter {
        fun onClickLogin()
    }
}
