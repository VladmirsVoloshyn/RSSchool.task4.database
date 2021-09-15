package com.example.petdatabase.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.petdatabase.room.PetsRepository
import java.lang.IllegalArgumentException

class MainViewModelFactory(private val repository: PetsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
            }
        throw IllegalArgumentException("Unknown class")
    }
}