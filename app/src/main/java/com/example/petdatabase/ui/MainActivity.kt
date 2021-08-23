package com.example.petdatabase.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petdatabase.R
import com.example.petdatabase.database.DatabaseManager
import com.example.petdatabase.databinding.ActivityMainBinding
import com.example.petdatabase.model.Pet
import com.example.petdatabase.ui.petlist.PetListAdapter

class MainActivity : AppCompatActivity(), AddFragment.OnAddPetListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var petBaseManager: DatabaseManager
    private var listPets = ArrayList<Pet>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        petBaseManager = DatabaseManager(this)

        val petListAdapter = PetListAdapter(listPets, this)
        binding.petList.adapter = petListAdapter
        binding.petList.layoutManager = LinearLayoutManager(this)

        binding.addButton.setOnClickListener {
            val addFragment = AddFragment()
            addFragment.show(supportFragmentManager, "sdf")
        }
    }

    override fun onPetAdd(pet: Pet) {
        listPets.add(pet)
        Log.d("MAIN ACTIVITY", listPets.size.toString())
    }
}