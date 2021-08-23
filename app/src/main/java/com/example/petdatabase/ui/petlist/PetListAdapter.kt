package com.example.petdatabase.ui.petlist

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Space
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petdatabase.R
import com.example.petdatabase.model.Pet
import kotlin.math.PI

class PetListAdapter(
    private val listPets: ArrayList<Pet>,
    private val context: Context
) : RecyclerView.Adapter<PetListAdapter.PetViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        return PetViewHolder(layoutInflater.inflate(R.layout.pet_item_container, parent, false))

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        holder.textName?.text = NAME_PATTERN +  listPets[position].name
        holder.textAge?.text = AGE_PATTERN + listPets[position].age.toString()
        holder.textGender?.text = GENDER_PATTERN + listPets[position].gender
        holder.textType?.text =  TYPE_PATTERN + listPets[position].type


        when(listPets[position].type){
            RACCOON -> holder.imageView?.setImageResource(R.drawable.raccoon)
            DOG -> holder.imageView?.setImageResource(R.drawable.dog)
            CAT -> holder.imageView?.setImageResource(R.drawable.cat)
            HORSE -> holder.imageView?.setImageResource(R.drawable.horse)
            SPIDER -> holder.imageView?.setImageResource(R.drawable.spider)
            LIZARD -> holder.imageView?.setImageResource(R.drawable.lizard)
            PIG -> holder.imageView?.setImageResource(R.drawable.pig)
        }

    }

    override fun getItemCount() = listPets.size

    inner class PetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textName: TextView? = null
        var textAge: TextView? = null
        var textGender: TextView? = null
        var textType: TextView? = null
        var imageView : ImageView? = null

        init {
            textName =itemView.findViewById(R.id.pet_name)
            textGender = itemView.findViewById(R.id.pet_gender)
            textAge = itemView.findViewById(R.id.pet_age)
            textType = itemView.findViewById(R.id.pet_type)
            imageView = itemView.findViewById(R.id.pet_avatar)
        }
    }
    companion object{
        private const val NAME_PATTERN = "Name : "
        private const val AGE_PATTERN = "Age : "
        private const val GENDER_PATTERN = "Gender : "
        private const val TYPE_PATTERN = "Type : "
        private const val RACCOON = "Raccoon"
        private const val CAT = "Cat"
        private const val DOG = "Dog"
        private const val HORSE = "Horse"
        private const val LIZARD = "Lizard"
        private const val SPIDER = "Spider"
        private const val PIG = "Pig"

    }

}