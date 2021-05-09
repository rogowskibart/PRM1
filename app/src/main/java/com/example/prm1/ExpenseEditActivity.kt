package com.example.prm1

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast
import com.example.prm1.database.Expense
import com.example.prm1.database.ExpensesDb
import com.example.prm1.databinding.ActivityExpenseEditBinding
import kotlinx.datetime.LocalDate
import java.util.*
import kotlin.concurrent.thread

class ExpenseEditActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private val binding by lazy { ActivityExpenseEditBinding.inflate(layoutInflater) }
    private lateinit var saveButton : Button
    private lateinit var cancelButton : Button

    val db by lazy { ExpensesDb.open(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Log.d("onCreate", "started onCreate")

        saveButton = binding.expenseEditSaveButton
        cancelButton = binding.expenseEditCancelButton

        binding.expenseEditDate.setOnClickListener {
            DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
            ).show()
        }
    }

    fun cancelButtonClicked(view: View) {
        Log.d("cancelButton", "cancel button clicked")
        val message = "does this work"
        val intent = Intent()
        intent.putExtra("MESSAGE", message.toString())
        setResult(2, intent)
        finish()
    }

    fun saveButtonClicked(view: View) {
        Toast.makeText(this, "You clicked Save", Toast.LENGTH_SHORT).show()
        Log.d("saveButton", "save button clicked")

        val expense = Expense(
            id = 0,
            place = binding.expenseEditPlaceEt.text.toString(),
            amount = binding.expenseEditAmount.text.toString().toIntOrNull() ?: 0,
            date = binding.expenseEditDate.text.toString(),
            category = binding.expenseEditCategory.text.toString()
        )

        println(expense)

        thread {
            db.expenses().insert(expense)
        }
        setResult(2)
        finish()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val date = LocalDate(year, month+1, dayOfMonth)
        binding.expenseEditDate.setText(date.toString())
    }
}