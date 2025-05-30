package com.example.mycookbook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashScreenViewModel: ViewModel() {
    private val _isReady = MutableStateFlow(true)
    val isReady: StateFlow<Boolean> = _isReady

    init {
        viewModelScope.launch {
            delay(2000)
            _isReady.value = false
        }
    }

}