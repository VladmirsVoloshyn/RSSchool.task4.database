package com.example.petdatabase.room

import androidx.annotation.WorkerThread
import com.example.petdatabase.model.Pet
import kotlinx.coroutines.flow.Flow


class PetsRepository(private val petDao: PetDao) {

    val allPets: Flow<List<Pet>> = petDao.getAlphabetizedWords()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(pet: Pet) {
        petDao.insert(pet)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(id: Int) {
        petDao.deleteById(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(pet: Pet) {
        petDao.update(pet)
    }

}