package com.example.petdatabase.ui.roomui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.petdatabase.model.Pet
import com.example.petdatabase.room.PetsRepository
import kotlinx.coroutines.launch

class RoomViewModel(private val repository: PetsRepository): ViewModel() {

    val pets: LiveData<List<Pet>> = repository.allPets.asLiveData()

    //room impl
    fun insert(pet: Pet) = viewModelScope.launch {
        repository.insert(pet)
    }

    fun delete(id: Int) = viewModelScope.launch {
        pets.value?.get(id)?.let { repository.delete(it.id) }
    }

    fun update(pet: Pet) = viewModelScope.launch {
        repository.update(pet)
    }
}