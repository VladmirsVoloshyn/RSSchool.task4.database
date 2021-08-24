package com.example.petdatabase.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petdatabase.R
import com.example.petdatabase.database.DatabaseManager
import com.example.petdatabase.databinding.ActivityMainBinding
import com.example.petdatabase.model.Pet
import com.example.petdatabase.ui.petlist.PetListAdapter
import java.text.FieldPosition

class MainActivity : AppCompatActivity(), AddFragment.OnAddPetListener, PetListAdapter.OnDeleteClickListener {

    private lateinit var binding: ActivityMainBinding
    private var listPets = ArrayList<Pet>()
    private var viewModel = MainViewModel()
    private var petListAdapter : PetListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.initialize(this)

        viewModel.getData().observe(this, Observer {
               petListAdapter = PetListAdapter(it, this, this)
               binding.petList.adapter = petListAdapter
               binding.petList.layoutManager = LinearLayoutManager(this)
        })


        binding.addButton.setOnClickListener {
            val addFragment = AddFragment()
            addFragment.show(supportFragmentManager, "sdf")
        }
    }

    override fun onPetAdd(pet: Pet) {
        viewModel.addPet(pet)
    }

    override fun onDeleteClick(position: Int) {
       viewModel.deletePet(position)
    }
}