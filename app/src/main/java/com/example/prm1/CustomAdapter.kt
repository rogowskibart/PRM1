package com.example.prm1

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.prm1.database.Expense
import com.example.prm1.database.ExpensesDb
import com.google.android.material.dialog.MaterialAlertDialogBuilder

internal class CustomAdapter(private var itemsList: List<Expense>) :
    RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var expenseIdView: TextView = view.findViewById(R.id.expenseId)
        var placeTextView: TextView = view.findViewById(R.id.place)
        var amountTextView: TextView = view.findViewById(R.id.amount)
        var dateTextView: TextView = view.findViewById(R.id.date)
        var categoryTextView: TextView = view.findViewById(R.id.category)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]
        holder.expenseIdView.text = item.id.toString()
        holder.placeTextView.text = item.place
        holder.amountTextView.text = item.amount.toString()
        if (item.amount > 0) {
            holder.amountTextView.setTextColor(Color.parseColor("#388E3C"))
        } else {
            holder.amountTextView.setTextColor(Color.parseColor("#D32F2F"))
        }
        holder.dateTextView.text = item.date
        holder.categoryTextView.text = item.category
        val itemId = item.id

        holder.itemView.setOnLongClickListener {
            MaterialAlertDialogBuilder(it.context)
                .setMessage("Do you want to delete expense with id $itemId?")
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.cancel()
                }
                .setPositiveButton("Delete") { dialog, which ->
                    val db by lazy { ExpensesDb.open(it.context) }
                    val expenseList = db.expenses().getAll()
                    val expenseForDeletion = expenseList.find { it.id == itemId }
                    expenseForDeletion?.let { it1 -> db.expenses().delete(it1) }
                    itemsList = db.expenses().getAll()
                    this.notifyDataSetChanged()
                }
                .show()
            true
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}