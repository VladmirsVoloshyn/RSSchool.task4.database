package com.example.petdatabase.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petdatabase.databinding.ActivityMainBinding
import com.example.petdatabase.model.Pet
import com.example.petdatabase.ui.petlist.PetListAdapter
import com.example.petdatabase.ui.preference.BaseSortPreference
import com.example.petdatabase.ui.preference.SettingsActivity
import com.example.petdatabase.util.ListPetSort
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), AddFragment.OnAddPetListener, PetListAdapter.OnDeleteClickListener {

    private lateinit var binding: ActivityMainBinding
    private var viewModel = MainViewModel()
    private var petListAdapter : PetListAdapter? = null
    private lateinit var sortMethod : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        sortMethod = preferences.getString(SORT_KEY, DEFAULT_SORT_METHOD)!!

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.initialize(this)

        viewModel.getData(sortMethod).observe(this, {
               petListAdapter = PetListAdapter(it, this, this)
               binding.petList.adapter = petListAdapter
               binding.petList.layoutManager = LinearLayoutManager(this)
        })
        binding.addButton.setOnClickListener {
            val addFragment = AddFragment()
            addFragment.show(supportFragmentManager, "sdf")
        }

        binding.preferenceFragmentButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onPetAdd(pet: Pet) {
        viewModel.addPet(pet)
        viewModel.sort(sortMethod)
    }

    override fun onDeleteClick(position: Int) {
        viewModel.deletePet(position)
        viewModel.sort(sortMethod)
    }

    override fun onResume() {
        super.onResume()
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        sortMethod = preferences.getString(SORT_KEY, DEFAULT_SORT_METHOD)!!
        viewModel.sort(sortMethod)
        println(sortMethod)
    }

    companion object {
        const val DEFAULT_SORT_METHOD = "None"
        const val SORT_KEY = "sort"
    }
}