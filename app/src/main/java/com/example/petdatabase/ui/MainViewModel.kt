package com.example.petdatabase.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petdatabase.sqlitedatabase.DatabaseManager
import com.example.petdatabase.model.Pet
import com.example.petdatabase.util.ListPetSort

class MainViewModel : ViewModel() {

    private var petList: MutableLiveData<ArrayList<Pet>> = MutableLiveData()
    private lateinit var dataBaseManager : DatabaseManager

    fun addPet(pet : Pet) {
        dataBaseManager.addPet(pet)
        petList.postValue(dataBaseManager.getPetsList())
    }

    fun getData(sortMethod : String): MutableLiveData<ArrayList<Pet>> {
        petList.postValue(ListPetSort.sort(sortMethod,dataBaseManager.getPetsList()))
        return petList
    }

    fun sort(sortMethod: String){
        petList.postValue(ListPetSort.sort(sortMethod, dataBaseManager.getPetsList()))
    }

    fun deletePet(position : Int) {
        val delID = petList.value?.get(position)?.id
        dataBaseManager.delete(delID!!)
        petList.postValue(dataBaseManager.getPetsList())
    }

    fun initialize(mContext : Context) {
        dataBaseManager = DatabaseManager(mContext)
    }

}