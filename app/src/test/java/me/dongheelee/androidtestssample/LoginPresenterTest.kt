package me.dongheelee.androidtestssample

import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.eq
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InOrder
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoinPresenterTests {

    @Mock
    lateinit var loginView: LoginContract.View

    @Mock
    lateinit var userRepository: UserRepository

    private lateinit var loginPresenter: LoginPresenter

    private lateinit var inOrder: InOrder

    private val loginResultCallback = argumentCaptor<UserRepositoryImpl.LoginResultCallback>()

    @Before
    fun setUp() {
        loginPresenter = LoginPresenter(loginView, userRepository)
        inOrder = inOrder(loginView, userRepository)
    }

    @Test
    fun `given invalid email, when login button is clicked, then can show toast with message`() {
        ArrangeBuilder()
            .withEmail("testeremail")
            .withPassword("qwer")

        loginPresenter.onClickLogin()

        verify(loginView).showMessageForInvalidEmail()
    }

    @Test
    fun `given invalid password, when login button is clicked, then can show toast with message`() {
        ArrangeBuilder()
            .withValidEmail()
            .withPassword("qwer")

        loginPresenter.onClickLogin()

        verify(loginView).showMessageForInvalidPassword()
    }

    @Test
    fun `given valid password, when login button is clicked, then can't show toast with message`() {
        ArrangeBuilder()
            .withValidEmail()
            .withValidPassword()

        loginPresenter.onClickLogin()

        verify(loginView, never()).showMessageForInvalidPassword()
    }

    @Test
    fun `given exist user account, when login button is clicked, then can show login success popup`() {
        ArrangeBuilder()
            .withValidEmail()
            .withValidPassword()

        loginPresenter.onClickLogin()

        inOrder.verify(loginView).hideKeyboard()
        inOrder.verify(loginView).showLoadingProgress()

        val email = loginView.getEmailText()
        val password = loginView.getPasswordText()

        inOrder.verify(userRepository).login(eq(email), eq(password), loginResultCallback.capture())
        loginResultCallback.firstValue.onSuccess()

        inOrder.verify(loginView).hideLoadingProgress()
        inOrder.verify(loginView).showPopupForLoginSuccess()
    }

    @Test
    fun `given not exist user account, when login button is clicked, then can show login success popup`() {
        ArrangeBuilder()
            .withEmail("tester@kakao.com")
            .withValidPassword()

        loginPresenter.onClickLogin()

        inOrder.verify(loginView).hideKeyboard()
        inOrder.verify(loginView).showLoadingProgress()

        val email = loginView.getEmailText()
        val password = loginView.getPasswordText()

        inOrder.verify(userRepository).login(eq(email), eq(password), loginResultCallback.capture())
        loginResultCallback.firstValue.onFailure()

        inOrder.verify(loginView).hideLoadingProgress()
        inOrder.verify(loginView).showMessageForLoginFailur()
    }

    inner class ArrangeBuilder {

        fun withEmail(email: String) = apply {
            given(loginView.getEmailText()).willReturn(email)
        }

        fun withValidEmail() = apply {
            withEmail("tester@google.com")
        }

        fun withValidPassword() = apply {
            withPassword("qwer1234")
        }

        fun withPassword(password: String) = apply {
            given(loginView.getPasswordText()).willReturn(password)
        }
    }
}
