package com.example.pizzacodes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pizzacodes.database.AppDatabase
import com.example.pizzacodes.database.Code
import com.example.pizzacodes.database.CodeRepository

class MainViewModel(private val database: AppDatabase) : ViewModel(){
    private val TAG: String = "MainViewModel"
    val allCodes: LiveData<List<Code>> = database.codeDao().getAllCodes()
}

class MainViewModelFactory(private val database: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}