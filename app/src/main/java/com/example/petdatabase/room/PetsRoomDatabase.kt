package com.example.petdatabase.room

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.room.Database

import androidx.room.RoomDatabase
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.petdatabase.model.Pet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Pet::class], version = 1, exportSchema = false)
abstract class PetsRoomDatabase : RoomDatabase() {

    abstract fun petDao(): PetDao

    private class PetsDataBaseCallBack(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {dataBase ->
                scope.launch {
                    populateDatabase(dataBase.petDao())
                }
            }
        }

        suspend fun populateDatabase(petDao: PetDao){
            petDao.deleteAll()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: PetsRoomDatabase? = null

        fun getDataBase(context: Context, scope: CoroutineScope): PetsRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PetsRoomDatabase::class.java,
                    "pets_database"
                ).addCallback(PetsDataBaseCallBack(scope)).build()
                INSTANCE = instance
                instance
            }
        }
    }


}