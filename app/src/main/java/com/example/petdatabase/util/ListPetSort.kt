package com.example.petdatabase.util

import com.example.petdatabase.model.Pet

class ListPetSort {
    companion object {
        fun sort(key: String, petList: ArrayList<Pet>): ArrayList<Pet> {
            return when (key) {
                KEY_AGE -> sortByAge(petList)
                KEY_NAME -> sortByName(petList)
                else -> petList
            }

        }
        private fun sortByName(petList: ArrayList<Pet>): ArrayList<Pet> {
            petList.sortBy { pet: Pet -> pet.name }
            return petList
        }

        private fun sortByAge(petList: ArrayList<Pet>): ArrayList<Pet> {
            petList.sortBy { pet: Pet -> pet.age }
            return petList
        }

        private const val KEY_NAME = "Name"
        private const val KEY_AGE = "Age"
    }


}