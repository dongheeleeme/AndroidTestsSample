package me.dongheelee.androidtestssample

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginContract.View {

    private lateinit var loginPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginPresenter = LoginPresenter(this, Injector.provideLoginRepository())

        flavorText.text = BuildConfig.FLAVOR
        loginButton.setOnClickListener {
            loginPresenter.onClickLogin()
        }
    }

    override fun getEmailText(): String = emailEditText.text.toString()

    override fun getPasswordText(): String = passwordEditText.text.toString()

    override fun showMessageForInvalidEmail() =
        Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show()

    override fun showMessageForInvalidPassword() =
        Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show()

    override fun showLoadingProgress() {
        loginButton.visibility = View.GONE
        loadingProgress.visibility = View.VISIBLE
    }

    override fun hideLoadingProgress() =
        runOnUiThread {
            loginButton.visibility = View.VISIBLE
            loadingProgress.visibility = View.GONE
        }

    override fun showPopupForLoginSuccess() =
        runOnUiThread {
            AlertDialog.Builder(this)
                .setMessage(getString(R.string.login_success_message))
                .show()
        }

    override fun showMessageForLoginFailur() =
        runOnUiThread {
            Toast.makeText(this, "Login Failure", Toast.LENGTH_SHORT).show()
        }

    override fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val inputMethodManager =
                this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
