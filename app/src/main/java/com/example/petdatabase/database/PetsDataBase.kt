package com.example.petdatabase.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PetsDataBase(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
                "create table " + TABLE_PETS + "("
                        + KEY_ID + " integer primary key,"
                        + KEY_NAME + " text,"
                        + KEY_AGE + " integer,"
                        + KEY_TYPE + " text,"
                        + KEY_GENDER + " text" + ")"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists $TABLE_PETS")
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "pets"
        const val DATABASE_VERSION = 1
        const val TABLE_PETS = "table_pets"
        const val KEY_ID = "_id"
        const val KEY_NAME = "name"
        const val KEY_AGE = "age"
        const val KEY_GENDER = "gender"
        const val KEY_TYPE = "type"
    }
}