package com.example.prm1.database

import androidx.room.*

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM Expense")
    fun getAll(): List<Expense>

    @Insert
    fun insert(expense: Expense)

    @Update
    fun update(expense: Expense)

    @Delete
    fun delete(expense: Expense)

    @Query("DELETE FROM Expense")
    fun deleteAll()
}