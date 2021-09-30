package com.example.libraandroid.ui.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.libraandroid.R
import com.example.libraandroid.service.network.ResponseException
import com.example.libraandroid.service.session.AccountSessionLoginManager
import com.example.libraandroid.ui.misc.DelayedCall
import com.example.libraandroid.ui.misc.UiConstant
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

class LoginViewModel(
    private val service: AccountSessionLoginManager
): ViewModel() {

    private val _loginResult = MutableSharedFlow<LoginResult>()
    val loginResult: SharedFlow<LoginResult> = _loginResult

    private var call = DelayedCall(coroutineScope = viewModelScope)
    fun login(username: String, password: String) {
        call.throttleFirst {
            try {
                service.login(username, password)
                _loginResult.emit(LoginResult.Success)
            } catch (e: ResponseException) {
                _loginResult.emit(LoginResult.Failure(e.message ?: ""))
            } catch (e: IOException) {
                Timber.e(e)
                _loginResult.emit(LoginResult.FailureId(R.string.g__text__connection_error))
            }
        }
    }
}

class LoginViewModelFactory(private val service: AccountSessionLoginManager): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return LoginViewModel(service) as T
    }
}