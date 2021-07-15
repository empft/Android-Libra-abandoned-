package com.example.libraandroid.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.libraandroid.R
import com.example.libraandroid.data.login.LoginRepository

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<Result<Unit>>()
    val loginResult: LiveData<Result<Unit>> = _loginResult

    fun login(username: String, password: String) {
        if (!localLoginCheck(username, password)) {
            return
        }

        // can be launched in a separate asynchronous job
        val result = loginRepository.login(username, password)

        if (result is Result.Success) {
            _loginResult.value = Result.success(Unit)
        } else {
            _loginResult.value = Result.failure(Exception())
        }
    }

    private fun localLoginCheck(username: String, password: String): Boolean {
        val nameValid = isUserNameValid(username)
        val passwordValid = isPasswordValid(password)

        val nameError = if (nameValid) null else R.string.g__edittext__invalid_username
        val passwordError = if (passwordValid) null else R.string.g__edittext__invalid_password

        _loginForm.value = LoginFormState(usernameError = nameError,passwordError = passwordError)
        return nameValid && passwordValid
    }

    // A username validation check
    private fun isUserNameValid(username: String): Boolean {
        return username.isNotBlank()
    }

    // A password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 8
    }
}