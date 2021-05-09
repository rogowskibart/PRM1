package com.example.prm1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prm1.database.Expense
import com.example.prm1.database.ExpensesDb
import com.example.prm1.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var customAdapter: CustomAdapter
    var expenses: List<Expense> = emptyList()

    val db by lazy { ExpensesDb.open(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val recyclerView = binding.myRecycler

        thread {
            expenses = db.expenses().getAll()
            val layoutManager = LinearLayoutManager(this@MainActivity)
            customAdapter = CustomAdapter(expenses)

            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = customAdapter

            customAdapter.notifyDataSetChanged()
            prepareData()
            refreshExpenses()
        }

        val bottomAppBar = binding.bottomAppBar

        bottomAppBar.setNavigationOnClickListener {
            Toast.makeText(this, "You clicked menu", Toast.LENGTH_SHORT).show()
        }

        bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search -> {
                    true
                }
                R.id.more -> {
                    true
                }
                else -> false
            }
        }

        val floatingButton = binding.floatingButton

        floatingButton.setOnClickListener {
            val intent = Intent(this, ExpenseEditActivity::class.java)
            startActivityForResult(intent, 2)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 2) {
            refreshExpenses()
        }
    }

    private fun refreshExpenses() {
        expenses = db.expenses().getAll()
        val sumPlus = expenses.filter { it.amount > 0 }.sumOf { it.amount }
        val sumMinus = expenses.filter { it.amount < 0 }.sumOf { it.amount }
        binding.sumPlus.text = sumPlus.toString()
        binding.sumMinus.text = sumMinus.toString()
        customAdapter.notifyDataSetChanged()
    }

    private fun prepareData() {
        db.expenses().deleteAll()
        db.expenses().insert(Expense(0, "Szef", 1500, "2021-04-01", "income"))
        db.expenses().insert(Expense(0, "Biedronka", -154, "2021-04-02", "food"))
        db.expenses().insert(Expense(0, "McDonald's", -20, "2021-04-04", "fast food"))
        db.expenses().insert(Expense(0, "Pan Slimak", -10, "2021-04-05", "lunch"))
        db.expenses().insert(Expense(0, "Pan Slimak", -12, "2021-04-06", "lunch"))
        db.expenses().insert(Expense(0, "Biedronka", -79, "2021-04-08", "food"))
        db.expenses().insert(Expense(0, "Warzywniak", -9, "2021-04-10", "food"))
        db.expenses().insert(Expense(0, "Kiosk", -5, "2021-04-14", "reading"))
        db.expenses().insert(Expense(0, "H&M", -99, "2021-04-15", "clothes"))
        db.expenses().insert(Expense(0, "Adidas", -129, "2021-04-20", "shoes"))
        db.expenses().insert(Expense(0, "Netflix", -50, "2021-04-24", "media"))
        db.expenses().insert(Expense(0, "Biedronka", -150, "2021-04-28", "food"))
        db.expenses().insert(Expense(0, "Netflix", -50, "2021-04-30", "shoes"))
    }
}

