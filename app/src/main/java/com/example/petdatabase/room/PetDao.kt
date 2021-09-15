package com.example.petdatabase.room

import androidx.room.*
import com.example.petdatabase.model.Pet
import kotlinx.coroutines.flow.Flow

@Dao
interface PetDao {

    @Query("SELECT * FROM table_pets")
    fun getAlphabetizedWords(): Flow<List<Pet>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pet: Pet)

    @Update
    suspend fun update(pet: Pet)

    @Query("DELETE FROM table_pets")
    suspend fun deleteAll()

    @Query("DELETE FROM table_pets WHERE _id = :id")
    suspend fun deleteById(id: Int)
}