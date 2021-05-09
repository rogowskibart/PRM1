package com.example.prm1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private const val DATABASE_FILENAME = "expenses"

@Database(version = 1, entities = [Expense::class])
abstract class ExpensesDb : RoomDatabase(){
    abstract fun expenses(): ExpenseDao

    companion object{
        fun open(context: Context): ExpensesDb =
            Room.databaseBuilder(
                context,
                ExpensesDb::class.java,
                DATABASE_FILENAME
            ).allowMainThreadQueries().build()
    }
}