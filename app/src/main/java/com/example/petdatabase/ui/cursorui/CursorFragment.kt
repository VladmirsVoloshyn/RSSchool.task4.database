package com.example.petdatabase.ui.cursorui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petdatabase.PetsApplication
import com.example.petdatabase.databinding.FragmentCursorBinding
import com.example.petdatabase.databinding.FragmentRoomBinding
import com.example.petdatabase.model.Pet
import com.example.petdatabase.ui.AddFragment
import com.example.petdatabase.ui.petlist.PetListAdapter
import com.example.petdatabase.ui.roomui.RoomViewModel
import com.example.petdatabase.ui.roomui.RoomViewModelFactory

class CursorFragment : Fragment(), PetListAdapter.OnDeleteClickListener, AddFragment.OnAddPetListener {
    private var _binding: FragmentCursorBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CursorViewModel by viewModels {
        CursorViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCursorBinding.inflate(inflater, container,false)
        viewModel.initialize(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getData().observe(requireActivity(), Observer {
            val petListAdapter = PetListAdapter(it, requireContext(), this)
            binding.petsList.adapter = petListAdapter
            binding.petsList.layoutManager = LinearLayoutManager(requireContext())
        })

        binding.addButton.setOnClickListener {
            val addFragment = AddFragment("new", Pet(1,"123",123,"123","123"))
            addFragment.show(requireActivity().supportFragmentManager, "add")
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDeleteClick(position: Int) {
        viewModel.deletePet(position)
    }

    override fun onEditClick(pet: Pet) {
        val addFragment = AddFragment("edit", Pet(1,"123",123,"123","123"))
        addFragment.show(requireActivity().supportFragmentManager, "add")
    }

    override fun onPetAdd(pet: Pet) {
        viewModel.addPet(pet)
    }

    override fun onPetEdit(pet: Pet) {
        viewModel.updatePet(pet)
    }
}