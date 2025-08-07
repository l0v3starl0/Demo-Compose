package com.example.demo.ui.theme.main

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel: ViewModel() {

    private val _uiState = MutableStateFlow<DataUiState>(DataUiState())
    val uiState: StateFlow<DataUiState> = _uiState

    fun fetchMoreData(){
        Log.d("Data", "More Data Fetched")
    }

    fun fetchData(){
        Log.d("Data", "Data Fetched")
    }
}

data class DataUiState(
    val value: String = "Test Data"
)