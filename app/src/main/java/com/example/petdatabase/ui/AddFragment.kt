package com.example.petdatabase.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.example.petdatabase.databinding.AddPetDialogFragmentBinding
import com.example.petdatabase.model.Pet
import com.example.petdatabase.shouldShowError

class AddFragment(private val callKey : String, val pet: Pet) : DialogFragment() {

    var mBinding: AddPetDialogFragmentBinding? = null
    private val binding get() = mBinding!!
    private var listener: OnAddPetListener? = null
    private var listGender = arrayOf("Male", "Female")
    private var listType = arrayOf("Cat", "Dog", "Horse", "Lizard", "Spider", "Raccoon", "Pig")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = AddPetDialogFragmentBinding.inflate(layoutInflater, container, false)

        if (callKey == "new"){
            binding.buttonAdd.text = "Add"
        }else{
            binding.buttonAdd.text = "Update"
        }
        return binding.root

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnAddPetListener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
        listener = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var gender = "Male"
        var type = "Cat"
        val genderAdapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            listGender
        )
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.petGenderList.adapter = genderAdapter

        val genderSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                gender = parent?.getItemAtPosition(position) as String
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                gender = "Male"
            }
        }
        binding.petGenderList.onItemSelectedListener = genderSelectedListener
        val typeSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                type = parent?.getItemAtPosition(position) as String
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                type = "Cat"
            }

        }
        binding.petTypeList.onItemSelectedListener = typeSelectedListener


        val typeAdapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, listType)
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.petTypeList.adapter = typeAdapter

        binding.buttonAdd.setOnClickListener {

            if (!binding.petNameEdit.shouldShowError(
                    "Please, set name",
                    binding.textInputLayoutName
                ) && !binding.petAgeEdit.shouldShowError(
                    "Please, set age",
                    binding.textInputLayoutAge
                )
            ) {
                if (callKey == "new"){ listener?.onPetAdd(
                    Pet(
                        name = binding.petNameEdit.text.toString(),
                        age = binding.petAgeEdit.text.toString().toInt(),
                        gender = gender,
                        type = type
                    )
                )
            }else{
                    listener?.onPetEdit(
                        Pet(id = pet.id,
                            name = binding.petNameEdit.text.toString(),
                            age = binding.petAgeEdit.text.toString().toInt(),
                            gender = gender,
                            type = type
                        )
                    )
            }
                dialog?.dismiss()
            }
        }
        binding.buttonSkip.setOnClickListener {
            dialog?.dismiss()
        }

    }

    interface OnAddPetListener {
        fun onPetAdd(pet: Pet)
        fun onPetEdit(pet : Pet)
    }
}