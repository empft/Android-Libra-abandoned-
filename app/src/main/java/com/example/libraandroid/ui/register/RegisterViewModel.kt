package com.example.libraandroid.ui.register

import android.os.CountDownTimer
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.libraandroid.R
import com.example.libraandroid.extension.isEmail
import com.example.libraandroid.ui.register.form.RegistrationForm
import com.example.libraandroid.ui.register.form.RegistrationFormState

class RegisterViewModel : ViewModel() {
    private val _reqCodeCooldown = MutableLiveData<Long?>()
    val reqCodeCooldown: LiveData<Long?> = _reqCodeCooldown
    private var timer: CountDownTimer? = null

    private val _formState = MutableLiveData<RegistrationFormState>()
    val formState: LiveData<RegistrationFormState> = _formState

    private val _form = RegistrationForm()
    val form: RegistrationForm
        get() = _form

    fun requestInvitationCode(email: String) {
        timer = object : CountDownTimer(60000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                _reqCodeCooldown.value = millisUntilFinished
            }
            override fun onFinish() {
                _reqCodeCooldown.value = null
            }
        }.start()



    }

    fun updateInvitation(email: String, code: String): Boolean {
        if (localInvitationCheck(email, code)) {
            _form.invitationEmail = email
            _form.invitationCode = code
            return true
        }
        return false
    }

    fun updateName(username: String, displayName: String): Boolean {
        if (localNameCheck(username, displayName)) {
            _form.username = username
            _form.displayName = displayName
            return true
        }
        return false
    }

    fun updatePassword(password: String): Boolean {
        if (localPasswordCheck(password)) {
            _form.password = password
            return true
        }
        return false
    }

    fun updateEmail(email: String): Boolean {
        if (localEmailCheck(email)) {
            _form.email = email
            return true
        }
        return false
    }

    fun submitForm(): Boolean {

    }

    private fun localInvitationCheck(email: String, code: String): Boolean {
        val emailValid = isInvitationEmailValid(email)
        val codeValid = isInvitationCodeValid(code)

        val emailError = if (emailValid) null else R.string.frag_regform__edittext__invalid_institutional_email
        val codeError = if (codeValid) null else R.string.frag_regform__edittext__invalid_code

        val state = formState.value ?: RegistrationFormState()
        _formState.value = state.copy(invitationEmailError = emailError, invitationCodeError = codeError)

        return emailValid && codeValid
    }

    private fun localNameCheck(username: String, displayName: String): Boolean {
        val userValid = isUsernameValid(username, displayName)
        val dispValid = isDisplayNameValid(username, displayName)

        val userError = if (userValid) null else R.string.frag_regform__edittext__user_same_as_display_name
        val dispError = if (dispValid) null else R.string.frag_regform__edittext__display_same_as_user_name

        val state = formState.value ?: RegistrationFormState()
        _formState.value = state.copy(usernameError = userError, displayNameError = dispError)
        return userValid && dispValid
    }

    private fun localPasswordCheck(password: String): Boolean {
        if (!isPasswordValid(password)) {
            val state = formState.value ?: RegistrationFormState()
            _formState.value = state.copy(passwordError = R.string.g__edittext__invalid_password)
            return false
        }
        return true
    }

    private fun localEmailCheck(email: String): Boolean {
        if (!isEmailValid(email)) {
            val state = formState.value ?: RegistrationFormState()
            _formState.value = state.copy(emailError = R.string.g__edittext__invalid_email)
            return false
        }
        return true
    }

    private fun isUsernameValid(username: String, displayName: String): Boolean {
        return username != displayName
    }

    private fun isDisplayNameValid(username: String, displayName: String): Boolean {
        return displayName != username
    }

    private fun isInvitationEmailValid(email: String): Boolean {
        return email.isEmail() && email.endsWith()
    }

    private fun isInvitationCodeValid(code: String): Boolean {
        return code.length == 6 && code.isDigitsOnly()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.isNotBlank()
    }

    private fun isEmailValid(email: String): Boolean {
        return email.isEmail()
    }
}