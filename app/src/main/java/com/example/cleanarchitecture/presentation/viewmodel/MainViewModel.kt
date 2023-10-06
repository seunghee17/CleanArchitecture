package com.example.cleanarchitecture.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitecture.data.model.Response
import com.example.cleanarchitecture.data.network.ApiResult
import com.example.cleanarchitecture.data.network.onFilure
import com.example.cleanarchitecture.data.network.onSuccess
import com.example.cleanarchitecture.domain.usecase.InfoUsecCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val infoUsecCase: InfoUsecCase
):ViewModel() {
    private val _errorState = MutableStateFlow<ApiResult.Failure?>(null)
    val errorState = _errorState.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _getResponse=MutableStateFlow(Response())
    val getResponse = _getResponse.asStateFlow()

    fun getResponse(){
        viewModelScope.launch {
            infoUsecCase()
                .onSuccess {
                    //ApiResult.Success내의 data속성 참조
                    _getResponse.value = it
                }
                .onFilure {
                    _errorState.value = it
                }
        }
    }
    fun onProgress(state:Boolean){
        viewModelScope.launch {
            _loading.emit(state)
        }
    }
}