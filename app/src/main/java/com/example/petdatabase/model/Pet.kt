package com.example.petdatabase.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "table_pets")
data class Pet(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "_id") val id: Int = 0 ,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "age") val age: Int,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "gender") val gender: String
)