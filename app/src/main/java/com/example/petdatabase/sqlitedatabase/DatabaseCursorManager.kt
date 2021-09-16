package com.example.petdatabase.sqlitedatabase

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.petdatabase.model.Pet

class DatabaseCursorManager(context: Context) {


    private var contentValues = ContentValues()
    private var listPets = ArrayList<Pet>()
    private var sqLiteDatabase: SQLiteDatabase

    init {
        val petsDataBase = PetsDataBase(context)
        sqLiteDatabase = petsDataBase.writableDatabase
    }

    fun getPetsList(): ArrayList<Pet> {
        fillListPets()
        return listPets
    }

    fun addPet(pet: Pet) {
        pet.apply {
            contentValues.put(PetsDataBase.KEY_NAME, name)
            contentValues.put(PetsDataBase.KEY_TYPE, type)
            contentValues.put(PetsDataBase.KEY_GENDER, gender)
            contentValues.put(PetsDataBase.KEY_AGE, age)
        }
        sqLiteDatabase.insert(PetsDataBase.TABLE_PETS, null, contentValues)
    }

    private fun fillListPets() {
        listPets.clear()
        val cursor: Cursor? =
            sqLiteDatabase.query(PetsDataBase.TABLE_PETS, null, null, null, null, null, null)
        if (cursor?.moveToFirst()!!) {
            val id = cursor.getColumnIndex(PetsDataBase.KEY_ID)
            val name = cursor.getColumnIndex(PetsDataBase.KEY_NAME)
            val gender = cursor.getColumnIndex(PetsDataBase.KEY_GENDER)
            val age = cursor.getColumnIndex(PetsDataBase.KEY_AGE)
            val type = cursor.getColumnIndex(PetsDataBase.KEY_TYPE)
            do {
                listPets.add(
                    Pet(
                        cursor.getInt(id),
                        cursor.getString(name),
                        cursor.getInt(age),
                        cursor.getString(type),
                        cursor.getString(gender)
                    )
                )
            } while (cursor.moveToNext())
        }
        Log.d("DataBase", "add new element")
        cursor.close()
    }

    fun delete(position: Int) {
        val delCount = sqLiteDatabase?.delete(
            PetsDataBase.TABLE_PETS,
            PetsDataBase.KEY_ID + "= " + position,
            null
        )
        Log.d("mLog", "delete rows count = $delCount")
        fillListPets()
    }

    fun updateElement(pet: Pet) {
        contentValues.put(PetsDataBase.KEY_NAME, pet.name)
        contentValues.put(PetsDataBase.KEY_AGE, pet.age)
        contentValues.put(PetsDataBase.KEY_TYPE, pet.type)
        contentValues.put(PetsDataBase.KEY_GENDER, pet.gender)
        sqLiteDatabase.update(
            PetsDataBase.TABLE_PETS,
            contentValues,
             "_id=?", Array(1){pet.id.toString()})
    }


}