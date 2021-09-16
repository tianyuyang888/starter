package com.udacity.shoestore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.UserInfo
import timber.log.Timber

class UserViewModel(state: SavedStateHandle) : ViewModel() {


    companion object {
        private const val USER_KEY = "userId"
        const val LOGIN_SUCCESSFUL: String = "LOGIN_SUCCESSFUL"
    }


    private val savedStateHandle = state

    private val _loginSuccess: MutableLiveData<Boolean> = savedStateHandle.getLiveData(LOGIN_SUCCESSFUL)
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    //// get userInfo from saveStateHandle
    private var _userInfo: MutableLiveData<UserInfo> = savedStateHandle.getLiveData(USER_KEY)
    val userInfo: LiveData<UserInfo> = _userInfo

    init {
        if (_userInfo.value == null) _userInfo.value = UserInfo("", "")
    }

    private val _eventRegister = MutableLiveData<Boolean>()
    val eventRegister: LiveData<Boolean>
        get() = _eventRegister

    private val _eventSignIn = MutableLiveData<Boolean>()
    val eventSignIn: LiveData<Boolean>
        get() = _eventSignIn

    fun onRegister() {
        // save userId
        if (_userInfo.value != null) {
            savedStateHandle.set(USER_KEY, _userInfo.value)
            savedStateHandle.set(LOGIN_SUCCESSFUL, true)
            _eventRegister.value = true
        }
    }

    fun onRegisterComplete() {
        _eventRegister.value = false
    }

    fun onSignIn() {
        // save userId
        Timber.i("email.value:${_userInfo.value?.email.toString()}")

        if (_userInfo.value != null) {
            savedStateHandle.set(USER_KEY, _userInfo.value)
            savedStateHandle.set(LOGIN_SUCCESSFUL, true)
            _eventSignIn.value = true
        }

    }

    fun onSignInComplete() {
        _eventSignIn.value = false
    }


    private val _eventNext = MutableLiveData<Boolean>()
    val eventNext: LiveData<Boolean>
        get() = _eventNext


    fun next() {
        _eventNext.value = true
    }

    fun onNextComplete() {
        _eventNext.value = false
    }
}