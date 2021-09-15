package com.example.petdatabase.ui.cursorui

import android.content.Context
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petdatabase.PetsApplication
import com.example.petdatabase.model.Pet
import com.example.petdatabase.sqlitedatabase.DatabaseCursorManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.example.petdatabase.ui.MainViewModel
import com.example.petdatabase.ui.MainViewModelFactory
import com.example.petdatabase.ui.roomui.RoomViewModel
import com.example.petdatabase.ui.roomui.RoomViewModelFactory
import com.example.petdatabase.util.ListPetSort

class CursorViewModel : ViewModel() {

    private var petList: MutableLiveData<List<Pet>> = MutableLiveData()
    private lateinit var dataBaseManager: DatabaseCursorManager

    fun initialize(mContext: Context) {
        dataBaseManager = DatabaseCursorManager(mContext)
    }

    fun getData(): MutableLiveData<List<Pet>> {
        petList.postValue(dataBaseManager.getPetsList())
        return petList
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