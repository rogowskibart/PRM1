package com.example.prm1.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Expense (
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    var place: String,
    var amount: Int,
    var date: String,
    var category: String
)