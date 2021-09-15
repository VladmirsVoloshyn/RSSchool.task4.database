package com.example.petdatabase.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petdatabase.PetsApplication
import com.example.petdatabase.databinding.ActivityMainBinding
import com.example.petdatabase.model.Pet
import com.example.petdatabase.ui.petlist.PetListAdapter
import com.example.petdatabase.ui.preference.SettingsActivity

class MainActivity : AppCompatActivity(), AddFragment.OnAddPetListener,
    PetListAdapter.OnDeleteClickListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as PetsApplication).repository)
    }
    private var petListAdapter: PetListAdapter? = null
    private lateinit var sortMethod: String
    private lateinit var dbImpl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        sortMethod = preferences.getString(SORT_KEY, DEFAULT_SORT_METHOD)!!
        dbImpl = preferences.getString(IMPL_KEY, DEFAULT_DB_IMPL)!!

        viewModel.initialize(application as PetsApplication)

        viewModel.getData(sortMethod, dbImpl).observe(this, {
            petListAdapter = PetListAdapter(it, this, this)
            binding.petList.adapter = petListAdapter
            binding.petList.layoutManager = LinearLayoutManager(this)
        })

        binding.addButton.setOnClickListener {
            val addFragment = AddFragment("new", Pet(999, "12", 12, "12", "12"))
            addFragment.show(supportFragmentManager, "sdf")
        }

        binding.preferenceFragmentButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onPetAdd(pet: Pet) {
        if (dbImpl == "Room") {
            viewModel.insert(pet)
        } else {
            viewModel.addPet(pet)
        }
        viewModel.sort(sortMethod)
    }

    override fun onPetEdit(pet: Pet) {
        if (dbImpl == "Room") {
            viewModel.update(pet)
        } else {
        }
        viewModel.sort(sortMethod)
    }

    override fun onDeleteClick(position: Int) {
        if (dbImpl == "Room") {
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
        dbImpl = preferences.getString(IMPL_KEY, DEFAULT_DB_IMPL)!!
        viewModel.getData(sortMethod,dbImpl)
        viewModel.sort(sortMethod)
    }

    companion object {
        const val DEFAULT_SORT_METHOD = "None"
        const val DEFAULT_DB_IMPL = "Room"
        const val SORT_KEY = "sort"
        const val IMPL_KEY = "impl"
    }
}