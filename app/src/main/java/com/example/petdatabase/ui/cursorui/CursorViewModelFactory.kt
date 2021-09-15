package com.example.petdatabase.ui.cursorui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.petdatabase.room.PetsRepository
import com.example.petdatabase.ui.MainViewModel
import java.lang.IllegalArgumentException

class CursorViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CursorViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return CursorViewModel() as T
        }
        throw IllegalArgumentException("Unknown class")
    }
}