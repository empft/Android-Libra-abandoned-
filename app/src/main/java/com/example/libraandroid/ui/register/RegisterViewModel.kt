package com.example.libraandroid.ui.register

import android.content.Context
import android.os.CountDownTimer
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.libraandroid.R
import com.example.libraandroid.domain.account.registration.AccountRegistrationInteractor
import com.example.libraandroid.miscellaneous.Either
import com.example.libraandroid.ui.misc.DelayedCall
import com.example.libraandroid.ui.time.formatMillis
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

private const val waitTime: Long = 60000
private const val intervalTime: Long = 700

class RegisterViewModel(
    private val service: AccountRegistrationInteractor
): ViewModel() {
    var invitationEmailError = mutableStateOf<String?>(null)
        private set
    var invitationCodeError = mutableStateOf<String?>(null)
        private set
    var usernameError = mutableStateOf<String?>(null)
        private set
    var displayNameError = mutableStateOf<String?>(null)
        private set
    var passwordError = mutableStateOf<String?>(null)
        private set
    var emailError = mutableStateOf<String?>(null)
        private set

    private val _reqCodeError = MutableSharedFlow<String>()
    val reqCodeError: SharedFlow<String> = _reqCodeError

    private val _registerFailure = MutableSharedFlow<RegisterFailure>()
    val registerFailure: SharedFlow<RegisterFailure> = _registerFailure

    private val _passwordResult = MutableSharedFlow<PasswordResult>()
    val passwordResult: SharedFlow<PasswordResult> = _passwordResult

    var countdown = mutableStateOf<String?>(null)
        private set
    private var timer: CountDownTimer? = null

    private val _registrationForm = RegistrationForm()
    val registrationForm: RegistrationForm
        get() = _registrationForm

    private val _registrationFormWithInvitation = RegistrationFormWithInvitation()
    val registrationFormWithInvitation: RegistrationFormWithInvitation
        get() = _registrationFormWithInvitation

    private var submitWithInvitationCall = DelayedCall(coroutineScope = viewModelScope)
    fun submitFormWithInvitation() {
        submitWithInvitationCall.throttleFirst {
            try {
                when(val result = service.registerWithInvitation(
                    registrationFormWithInvitation.username,
                    registrationFormWithInvitation.displayName,
                    registrationFormWithInvitation.password,
                    registrationFormWithInvitation.email,
                    registrationFormWithInvitation.invitationEmail,
                    registrationFormWithInvitation.invitationCode
                )) {
                    is Either.Failure -> {
                        usernameError.value = result.value.username
                        displayNameError.value = result.value.displayName
                        passwordError.value = result.value.password
                        emailError.value = result.value.email
                        invitationEmailError.value = result.value.invitationEmail
                        invitationCodeError.value = result.value.invitationCode

                        _registerFailure.emit(RegisterFailure.Form)
                    }
                    is Either.Success -> {
                        // Success!
                        // Changes handled by flow in business logic
                    }
                }
            } catch (e: IOException) {
                Timber.e(e)
                _registerFailure.emit(RegisterFailure.Id(R.string.g__text__connection_error))
            }
        }
    }

    private var submitCall = DelayedCall(coroutineScope = viewModelScope)
    fun submitForm() {
        submitCall.throttleFirst {
            try {
                when(val result = service.register(
                    registrationForm.username,
                    registrationForm.displayName,
                    registrationForm.password,
                    registrationForm.email
                )) {
                    is Either.Failure -> {
                        usernameError.value = result.value.username
                        displayNameError.value = result.value.displayName
                        passwordError.value = result.value.password
                        emailError.value = result.value.email

                        _registerFailure.emit(RegisterFailure.Form)
                    }
                    is Either.Success -> {
                        // Success!
                        // Changes handled by flow in business logic
                    }
                }
            } catch (e: IOException) {
                Timber.e(e)
                _registerFailure.emit(RegisterFailure.Id(R.string.g__text__connection_error))
            }
        }
    }

    fun checkAndSetInvitation(context: Context, email: String, code: String): Boolean {
        if (!email.contains('@')) {
            invitationEmailError.value = context.resources.getString(R.string.g__error__invalid_email)
            return false
        }

        invitationEmailError.value = null
        invitationCodeError.value = null
        _registrationFormWithInvitation.invitationEmail = email
        _registrationFormWithInvitation.invitationCode = code
        return true
    }

    fun checkAndSetName(context: Context, username: String, displayName: String): Boolean {
        if (username == displayName) {
            usernameError.value = context.resources.getString(R.string.scr_regform__error__user_same_as_display_name)
            displayNameError.value = context.resources.getString(R.string.scr_regform__error__display_same_as_user_name)
            return false
        }

        usernameError.value = null
        displayNameError.value = null

        _registrationFormWithInvitation.username = username
        _registrationFormWithInvitation.displayName = displayName

        _registrationForm.username = username
        _registrationForm.displayName = displayName
        return true
    }

    fun checkAndSetPassword(context: Context, password: String) {
        viewModelScope.launch {
            _passwordResult.emit(PasswordResult.Loading)

            when {
                password.length < 8 -> {
                    passwordError.value = context.resources.getString(R.string.scr_regform__error__short_password)
                    _passwordResult.emit(PasswordResult.Empty)

                }
                matchCommonCaseInsensitive(context, password) -> {
                    passwordError.value = context.resources.getString(R.string.scr_regform__error__common_password)
                    _passwordResult.emit(PasswordResult.Empty)
                }
                else -> {
                    passwordError.value = null

                    _registrationFormWithInvitation.password = password
                    _registrationForm.password = password

                    _passwordResult.emit(PasswordResult.Success)
                }
            }
        }
    }

    fun checkAndSetEmail(context: Context, email: String): Boolean {
        if (!email.contains('@')) {
            emailError.value = context.resources.getString(R.string.g__error__invalid_email)
            return false
        }

        emailError.value = null

        _registrationFormWithInvitation.email = email
        _registrationForm.email = email
        return true
    }

    private fun startCountdown(timeInMillis: Long) {
        timer = object: CountDownTimer(timeInMillis, intervalTime) {
            override fun onTick(millisUntilFinished: Long) {
                countdown.value = formatMillis(millisUntilFinished)
            }

            override fun onFinish() {
                countdown.value = null
                timer = null
            }
        }
        timer?.start()
    }

    private fun cancelCountdown() {
        timer?.cancel()
    }

    private var reqCodeCall = DelayedCall(coroutineScope = viewModelScope)
    fun requestInvitationCode(context: Context, email: String) {
        reqCodeCall.throttleFirst {
            try {
                this.startCountdown(waitTime)
                when(val result = service.createInvitation(email)) {
                    is Either.Failure -> {
                        cancelCountdown()
                        _reqCodeError.emit(result.value)
                    }
                    is Either.Success -> {
                        // Do nothing on success since it is out of band?
                        // Maybe show email successfully sent message
                    }
                }
            } catch (e: IOException) {
                cancelCountdown()
                Timber.e(e)
                _reqCodeError.emit(context.resources.getString(R.string.g__text__connection_error))
            }
        }
    }

    private fun matchCommonCaseInsensitive(context: Context, password: String): Boolean {
        context.assets.open("commonpassword.txt").bufferedReader().useLines {
            it.forEach { line ->
                if (line.equals(password, ignoreCase = true)) {
                    return true
                }
            }
        }
        return false
    }
}

class RegisterViewModelFactory(private val api: AccountRegistrationInteractor): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return RegisterViewModel(api) as T
    }
}