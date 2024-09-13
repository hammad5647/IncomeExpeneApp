package com.example.budgetmanagmentapp

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import com.example.budgetmanagmentapp.activities.AddEntryActivity
import com.example.budgetmanagmentapp.adapter.EntryAdapter
import com.example.budgetmanagmentapp.databinding.ActivityMainBinding
import com.example.budgetmanagmentapp.entity.TraEntity
import com.example.budgetmanagmentapp.helper.DBHelper.Companion.db
import com.example.budgetmanagmentapp.helper.DBHelper.Companion.initDatabase
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: EntryAdapter
    private var readList = mutableListOf<TraEntity>()
    private var searchedList = listOf<TraEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clickInit()
        initAdapter()
    }

    private fun clickInit() {
        binding.addBtn.setOnClickListener {
            startActivity(Intent(this, AddEntryActivity::class.java))
        }
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchedList = readList.filter {
                    it.traCategory.lowercase(Locale.getDefault())
                        .contains(newText!!.lowercase(Locale.getDefault()))
                }
                return false
            }
        })
    }

    private fun initAdapter() {
        adapter = EntryAdapter(readList)
        binding.homeRecyclerView.adapter = adapter

    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        initDatabase(this)
        readList = db!!.dao().readData()
        val amount = db!!.dao().getExpense()
        if (amount == null) {
            binding.expenseTxt.text = "₹0"
        } else {
            db!!.dao().getExpense()
            binding.expenseTxt.text = "₹$amount"
        }
        val income = db!!.dao().getIncome()
        if (income == null) {
            binding.incomeTxt.text = "₹0"
        } else {
            db!!.dao().getIncome()
            binding.incomeTxt.text = "₹$income"
        }
        adapter.notifyDataChanged(readList)

        super.onResume()
    }
}