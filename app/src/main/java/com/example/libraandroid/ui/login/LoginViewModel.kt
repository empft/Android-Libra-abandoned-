package com.example.libraandroid.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.libraandroid.R
import com.example.libraandroid.domain.applicationsession.ApplicationLoginInteractor
import com.example.libraandroid.miscellaneous.Either
import com.example.libraandroid.ui.misc.DelayedCall
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

class LoginViewModel(
    private val service: ApplicationLoginInteractor
): ViewModel() {

    private val _loginFailure = MutableSharedFlow<LoginFailure>()
    val loginFailure: SharedFlow<LoginFailure> = _loginFailure

    private var call = DelayedCall(coroutineScope = viewModelScope)
    fun login(username: String, password: String) {
        call.throttleFirst {
            try {
                when(val result = service.login(username, password)) {
                    is Either.Failure -> {
                        _loginFailure.emit(LoginFailure.Text(result.value.message))
                    }
                    is Either.Success -> {
                        // Success!
                        // Changes handled by flow in business logic
                    }
                }
            } catch (e: IOException) {
                Timber.e(e)
                _loginFailure.emit(LoginFailure.Id(R.string.g__text__connection_error))
            }
        }
    }

    fun guestLogin() {
        call.throttleFirst {
            service.guestLogin()
        }
    }
}

class LoginViewModelFactory(
    private val service: ApplicationLoginInteractor
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return LoginViewModel(service) as T
    }
}