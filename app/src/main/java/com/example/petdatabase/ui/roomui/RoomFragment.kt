package com.example.petdatabase.ui.roomui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petdatabase.PetsApplication
import com.example.petdatabase.databinding.FragmentRoomBinding
import com.example.petdatabase.model.Pet
import com.example.petdatabase.ui.AddFragment
import com.example.petdatabase.ui.petlist.PetListAdapter

class RoomFragment : Fragment(), AddFragment.OnAddPetListener, PetListAdapter.OnDeleteClickListener {

    private val viewModel: RoomViewModel by viewModels {
        RoomViewModelFactory((requireActivity().application as PetsApplication).repository)
    }

    private var _binding: FragmentRoomBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.pets.observe(requireActivity(), {
            val adapter = PetListAdapter(it, requireActivity(), this)
            binding.petsList.adapter = adapter
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

    override fun onPetAdd(pet: Pet) {
        viewModel.insert(pet)
    }

    override fun onPetEdit(pet: Pet) {
        viewModel.update(pet)
    }

    override fun onDeleteClick(position: Int) {
       viewModel.delete(position)
    }

    override fun onEditClick(pet: Pet) {
        val addFragment = AddFragment("edit", pet)
        addFragment.show(requireActivity().supportFragmentManager, "edit")
    }
}