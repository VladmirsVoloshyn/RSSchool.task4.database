package com.example.petdatabase

import android.app.Application
import com.example.petdatabase.room.PetsRepository
import com.example.petdatabase.room.PetsRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


@Suppress("unused")
class PetsApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())
    private val dataBase by lazy {
        PetsRoomDatabase.getDataBase(this, applicationScope)
    }
    val repository by lazy {
        PetsRepository(dataBase.petDao())
    }

}