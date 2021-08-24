package com.example.petdatabase.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petdatabase.database.DatabaseManager
import com.example.petdatabase.model.Pet

class MainViewModel : ViewModel() {

    private var petList: MutableLiveData<ArrayList<Pet>> = MutableLiveData()
    private lateinit var context : Context
    private lateinit var dataBaseManager : DatabaseManager

    fun addPet(pet : Pet) {
        dataBaseManager.addPet(pet)
        petList.postValue(dataBaseManager.getPetsList())
    }

    fun getData(): MutableLiveData<ArrayList<Pet>> {
        petList.postValue(dataBaseManager.getPetsList())
        return petList
    }

    fun deletePet(position : Int) {
        dataBaseManager.delete(position)
        petList.postValue(dataBaseManager.getPetsList())
        getData()
    }

    fun initialize(mContext : Context) {
        this.context = mContext
        dataBaseManager = DatabaseManager(context)
    }

}