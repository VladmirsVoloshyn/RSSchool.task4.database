package com.example.petdatabase.ui.roomui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.petdatabase.room.PetsRepository
import com.example.petdatabase.ui.MainViewModel
import java.lang.IllegalArgumentException

class RoomViewModelFactory(private val repository: PetsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoomViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return RoomViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown class")
    }
}