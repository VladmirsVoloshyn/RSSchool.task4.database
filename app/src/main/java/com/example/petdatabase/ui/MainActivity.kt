package com.example.petdatabase.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentTransaction
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petdatabase.PetsApplication
import com.example.petdatabase.databinding.ActivityMainBinding
import com.example.petdatabase.model.Pet
import com.example.petdatabase.ui.cursorui.CursorFragment
import com.example.petdatabase.ui.petlist.PetListAdapter
import com.example.petdatabase.ui.preference.SettingsActivity
import com.example.petdatabase.ui.roomui.RoomFragment

class MainActivity : AppCompatActivity(), AddFragment.OnAddPetListener,
    PetListAdapter.OnDeleteClickListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as PetsApplication).repository)
    }
    private var petListAdapter: PetListAdapter? = null
    private lateinit var sortMethod: String
    private lateinit var dbImpl: String
    private var fragmentID = "room"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)

        val roomFragment = RoomFragment()
        val fragmentTransaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.petList.id,roomFragment).commit()
        binding.baseLabelTextView.text = "Pet database (ROOM)"

        binding.changeImplButton.setOnClickListener {
            fragmentID = if (fragmentID == "room"){
                val fragment = CursorFragment()
                val fragmentTransaction : FragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(binding.petList.id,fragment ).commit()
                binding.baseLabelTextView.text = "Pet database (CURSOR)"
                "cursor"
            }else{
                val fragment = RoomFragment()
                val fragmentTransaction : FragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(binding.petList.id,fragment ).commit()
                binding.baseLabelTextView.text = "Pet database (ROOM)"
                "room"
            }
        }




        sortMethod = preferences.getString(SORT_KEY, DEFAULT_SORT_METHOD)!!
        dbImpl = preferences.getString(IMPL_KEY, DEFAULT_DB_IMPL)!!

        viewModel.initialize(application as PetsApplication)

        viewModel.getData(sortMethod, dbImpl).observe(this, {
            petListAdapter = PetListAdapter(it, this, this)
        })



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
            viewModel.updatePet(pet)
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