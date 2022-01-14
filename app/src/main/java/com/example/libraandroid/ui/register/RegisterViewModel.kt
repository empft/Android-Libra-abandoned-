package com.example.libraandroid.ui.register

import android.content.Context
import android.os.CountDownTimer
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.libraandroid.R
import com.example.libraandroid.domain.account.AccountRegistrationService
import com.example.libraandroid.network.NamedError
import com.example.libraandroid.ui.time.formatMillis
import com.example.libraandroid.ui.misc.DelayedCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

private const val waitTime: Long = 60000
private const val intervalTime: Long = 700

class RegisterViewModel(
    private val api: AccountRegistrationService
): ViewModel() {
    var invitationEmailError = mutableStateOf<Int?>(null)
        private set
    var invitationCodeError = mutableStateOf<Int?>(null)
        private set
    var usernameError = mutableStateOf<Int?>(null)
        private set
    var displayNameError = mutableStateOf<Int?>(null)
        private set
    var passwordError = mutableStateOf<Int?>(null)
        private set
    var emailError = mutableStateOf<Int?>(null)
        private set

    private val _userError = MutableSharedFlow<String>()
    val userError: SharedFlow<String> = _userError

    private val _registerResult = MutableSharedFlow<Boolean>()
    val registerResult: SharedFlow<Boolean> = _registerResult

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
                api.registerWithInvitation(
                    registrationFormWithInvitation.username,
                    registrationFormWithInvitation.displayName,
                    registrationFormWithInvitation.password,
                    registrationFormWithInvitation.email,
                    registrationFormWithInvitation.invitationEmail,
                    registrationFormWithInvitation.invitationCode
                )
                _registerResult.emit(true)
            } catch (e: HttpException) {

                _registerResult.emit(false)
            }
        }
    }

    private var submitCall = DelayedCall(coroutineScope = viewModelScope)
    fun submitForm() {
        submitCall.throttleFirst {
            api.register(
                registrationForm.username,
                registrationForm.displayName,
                registrationForm.password,
                registrationForm.email
            )
            _registerResult.emit(true)
        }
    }

    fun checkAndSetInvitation(email: String, code: String): Boolean {
        if (!email.contains('@')) {
            invitationEmailError.value = R.string.g__error__invalid_email
            return false
        }

        invitationEmailError.value = null
        invitationCodeError.value = null
        _registrationFormWithInvitation.invitationEmail = email
        _registrationFormWithInvitation.invitationCode = code
        return true
    }

    fun checkAndSetName(username: String, displayName: String): Boolean {
        if (username == displayName) {
            usernameError.value = R.string.scr_regform__error__user_same_as_display_name
            displayNameError.value = R.string.scr_regform__error__display_same_as_user_name
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
                    passwordError.value = R.string.scr_regform__error__short_password
                    _passwordResult.emit(PasswordResult.Empty)

                }
                matchCommonCaseInsensitive(context, password) -> {
                    passwordError.value = R.string.scr_regform__error__common_password
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

    fun checkAndSetEmail(email: String): Boolean {
        if (!email.contains('@')) {
            emailError.value = R.string.g__error__invalid_email
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
    fun requestInvitationCode(email: String, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        reqCodeCall.throttleFirst {
            try {
                api.createInvitation(email)
                this.startCountdown(waitTime)
            } catch(e: HttpException) {
                e.response()?.errorBody()?.let {
                    val error = NamedError.from(it.charStream().readText())
                    _userError.emit(error.message)

                    withContext(dispatcher) {
                        it.close()
                    }
                }
                cancelCountdown()
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

class RegisterViewModelFactory(private val api: AccountRegistrationService): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return RegisterViewModel(api) as T
    }
}