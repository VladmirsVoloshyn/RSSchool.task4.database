package com.example.petdatabase.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.petdatabase.model.Pet
import kotlinx.coroutines.flow.Flow

@Dao
interface PetDao {

    @Query("SELECT * FROM table_pets")
    fun getAlphabetizedWords(): Flow<List<Pet>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pet: Pet)

    @Query("DELETE FROM table_pets")
    suspend fun deleteAll()
}