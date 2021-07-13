package com.example.libraandroid.ui.register.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegFormViewModel: ViewModel() {

    private val _formState = MutableLiveData<RegistrationFormState>()
    val formState: LiveData<RegistrationFormState> = _formState


    private fun isUsernameValid() {

    }

    private fun isDisplayNameValid() {

    }


}