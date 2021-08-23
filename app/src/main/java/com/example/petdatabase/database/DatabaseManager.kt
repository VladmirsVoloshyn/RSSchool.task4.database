package com.example.petdatabase.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.petdatabase.model.Pet

class DatabaseManager(context: Context) {

    private val phoneBaseManagerContext = context
    private var sqLiteDatabase: SQLiteDatabase
    private var contentValues = ContentValues()
    private var listPets = ArrayList<Pet>()

    init {
        val phoneBase = PetsDataBase(phoneBaseManagerContext)
        sqLiteDatabase = phoneBase.writableDatabase
    }

    fun getPetsList(): ArrayList<Pet>{
        fillListPets()
        return listPets
    }

    fun addPet(pet: Pet) {
        (pet as Pet).apply {
            contentValues.put(PetsDataBase.KEY_NAME, name)
            contentValues.put(PetsDataBase.KEY_TYPE, type)
            contentValues.put(PetsDataBase.KEY_GENDER, gender)
            contentValues.put(PetsDataBase.KEY_AGE, age)
        }
        Log.d("DataBase", "add new element")
        sqLiteDatabase.insert(PetsDataBase.TABLE_PETS, null, contentValues)
    }

    private fun fillListPets() {
        listPets.clear()
        val cursor: Cursor =
            sqLiteDatabase.query(PetsDataBase.TABLE_PETS, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            val id = cursor.getColumnIndex(PetsDataBase.KEY_ID)
            val name = cursor.getColumnIndex(PetsDataBase.KEY_NAME)
            val gender = cursor.getColumnIndex(PetsDataBase.KEY_GENDER)
            val age = cursor.getColumnIndex(PetsDataBase.KEY_AGE)
            val type = cursor.getColumnIndex(PetsDataBase.KEY_TYPE)
            do {
                listPets.add(
                    Pet(
                        cursor.getString(name),
                        cursor.getInt(age),
                        cursor.getString(gender),
                        cursor.getString(type)
                    )
                )
            } while (cursor.moveToNext())
        }
        Log.d("DataBase", "add new element")
        cursor.close()
    }
}