package com.example.petdatabase.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petdatabase.PetsApplication
import com.example.petdatabase.databinding.ActivityMainBinding
import com.example.petdatabase.model.Pet
import com.example.petdatabase.ui.petlist.PetListAdapter
import com.example.petdatabase.ui.preference.SettingsActivity
import com.example.petdatabase.util.ListPetSort

class MainActivity : AppCompatActivity(), AddFragment.OnAddOrEditPetListener,
    PetListAdapter.OnPetListButtonClickListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as PetsApplication).repository)
    }
    private lateinit var sortMethod: String
    private var listID = "room"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)

        binding.changeImplButton.setOnClickListener {
            if (listID == "room"){
                binding.petListRoom.visibility = RecyclerView.GONE
                binding.petListCursor.visibility = RecyclerView.VISIBLE
                binding.baseLabelTextView.text = "Pet database (CURSOR)"
                listID = "cursor"
            }else {
                binding.petListRoom.visibility = RecyclerView.VISIBLE
                binding.petListCursor.visibility = RecyclerView.GONE
                binding.baseLabelTextView.text = "Pet database (ROOM)"
                listID = "room"
            }
        }

        sortMethod = preferences.getString(SORT_KEY, DEFAULT_SORT_METHOD)!!
        viewModel.initialize(application as PetsApplication)

        viewModel.pets.observe(this, {
            val petListAdapter = PetListAdapter(ListPetSort.sort(sortMethod, it.toCollection(ArrayList())), this, this)
            binding.petListRoom.adapter = petListAdapter
            binding.petListRoom.layoutManager = LinearLayoutManager(applicationContext)
        })

        viewModel.getData(sortMethod).observe(this, {
            val petListAdapter = PetListAdapter(it, this, this)
            binding.petListCursor.adapter = petListAdapter
            binding.petListCursor.layoutManager = LinearLayoutManager(applicationContext)
        })

        binding.addButton.setOnClickListener {
            val addFragment = AddFragment("new", Pet(1,"123",123,"123","123"))
            addFragment.show(supportFragmentManager, "sdf")
        }

        binding.preferenceFragmentButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onPetAdd(pet: Pet) {
        if (listID == "room") {
            viewModel.insert(pet)
        }
        if (listID == "cursor"){
            viewModel.addPet(pet)
        }
        viewModel.sort(sortMethod)
    }

    override fun onPetEdit(pet: Pet) {
        if (listID == "room") {
            viewModel.update(pet)
        }
        if (listID == "cursor"){
            viewModel.updatePet(pet)
        }
        viewModel.sort(sortMethod)
    }

    override fun onDeleteClick(position: Int) {
        if (listID == "room") {
            viewModel.delete(position)
        } else {
            viewModel.deletePet(position)
        }
        viewModel.sort(sortMethod)
    }

    override fun onEditClick(pet: Pet) {
        val addFragment = AddFragment("edit", pet)
        addFragment.show(supportFragmentManager, "sdf")
    }

    override fun onResume() {
        super.onResume()
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        sortMethod = preferences.getString(SORT_KEY, DEFAULT_SORT_METHOD)!!
        viewModel.sort(sortMethod)
    }

    companion object {
        const val DEFAULT_SORT_METHOD = "None"
        const val DEFAULT_DB_IMPL = "Room"
        const val SORT_KEY = "sort"
        const val IMPL_KEY = "impl"
    }
}