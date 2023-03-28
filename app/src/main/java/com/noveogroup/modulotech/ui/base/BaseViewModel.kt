package com.noveogroup.modulotech.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val _message: MutableStateFlow<String?> = MutableStateFlow(null)
    val message: StateFlow<String?> = _message

    /**
     * The logic can be different. At the moment it shows a message for [MESSAGE_TIME] milliseconds and then hides it.
     */
    fun showMessage(message: String) {
        viewModelScope.launch {
            _message.value = message
            delay(MESSAGE_TIME)
            _message.value = null
        }
    }

    companion object {
        private const val MESSAGE_TIME = 2000L
    }
}
