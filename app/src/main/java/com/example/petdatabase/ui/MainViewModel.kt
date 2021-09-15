package com.example.petdatabase.ui

import android.content.Context
import androidx.lifecycle.*
import com.example.petdatabase.model.Pet
import com.example.petdatabase.room.PetsRepository
import com.example.petdatabase.room.PetsRoomDatabase
import com.example.petdatabase.sqlitedatabase.DatabaseCursorManager
import com.example.petdatabase.util.ListPetSort
import kotlinx.coroutines.launch

class MainViewModel(private val repository: PetsRepository) : ViewModel() {

    private val pets: LiveData<List<Pet>> = repository.allPets.asLiveData()

    private var petList: MutableLiveData<List<Pet>> = MutableLiveData()
    private lateinit var dataBaseManager: DatabaseCursorManager

    fun getData(sortMethod: String, impl: String): MutableLiveData<List<Pet>> {
        if (impl == "Room") {
            return repository.allPets.asLiveData() as MutableLiveData<List<Pet>>
        } else if (impl == "Cursor") {
            petList.postValue(ListPetSort.sort(sortMethod, dataBaseManager.getPetsList()))
        }
        return petList
    }

    fun sort(sortMethod: String) {
        petList.postValue(ListPetSort.sort(sortMethod, dataBaseManager.getPetsList()))
    }

    fun initialize(mContext: Context) {
        dataBaseManager = DatabaseCursorManager(mContext)
    }

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

    //cursor impl
    fun addPet(pet: Pet) {
        dataBaseManager.addPet(pet)
        petList.postValue(dataBaseManager.getPetsList())
    }

    fun deletePet(position: Int) {
        val delID = petList.value?.get(position)?.id
        dataBaseManager.delete(delID!!)
        petList.postValue(dataBaseManager.getPetsList())
    }

    fun updatePet(pet: Pet) {
        dataBaseManager.updateElement(pet)
    }


}