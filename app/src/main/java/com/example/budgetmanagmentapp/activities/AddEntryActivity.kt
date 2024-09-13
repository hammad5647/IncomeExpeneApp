package com.example.budgetmanagmentapp.activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.budgetmanagmentapp.R
import com.example.budgetmanagmentapp.databinding.ActivityAddEntryBinding
import com.example.budgetmanagmentapp.entity.TraEntity
import com.example.budgetmanagmentapp.helper.DBHelper.Companion.db
import com.example.budgetmanagmentapp.helper.DBHelper.Companion.initDatabase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddEntryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEntryBinding
    private var transactionType = 1
    private var getId: Int = -1
    private var date: Calendar? = Calendar.getInstance()
    private var todaysDate = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentData()
        initClick()

    }

    private fun insertUpdateData() {
        val title = binding.txtTitle.text.toString()
        val amount = binding.traAmount.text.toString()
        val category = binding.txtTraCategory.text.toString()
        val date = binding.dateTxt.text.toString()
        if (category.isEmpty()) {
            binding.textInputLayoutCategory.error = "Please Enter Category"
        } else if (amount.isEmpty()) {
            binding.textInputLayoutAmount.error = "Please Enter Amount"
        } else if (title.isEmpty()) {
            binding.textInputLayoutTitle.error = "Please Enter Title"
        } else {
            val transactionEntity = TraEntity(
                traTitle = title,
                traCategory = category,
                traDate = date,
                traAmount = amount,
                traType = transactionType

            )
            if (getId == -1) {
                initDatabase(this)
                db!!.dao().insertData(transactionEntity)
                finish()
            } else {
                transactionEntity.traId = getId
                initDatabase(this)
                db!!.dao().updateData(transactionEntity)
                finish()
            }
        }
    }

    private fun initClick() {
        transactionTypes()
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.addEntryBtn.setOnClickListener {
            insertUpdateData()
        }
        binding.dateTxt.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    todaysDate = dateFormat.format(selectedDate.time)
                    binding.dateTxt.text = todaysDate
                },
                date!!.get(Calendar.YEAR),
                date!!.get(Calendar.MONTH),
                date!!.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.datePicker.maxDate = date!!.timeInMillis
            datePickerDialog.show()
        }
    }

    private fun transactionTypes() {
        binding.radioBtnGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.incomeRadio -> {
                    transactionType = 1
                }

                R.id.expenseRadio -> {
                    transactionType = 2
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun getIntentData() {
        val intent = intent
        getId = intent.getIntExtra("id", -1)
        val title1 = intent.getStringExtra("title")
        val category1 = intent.getStringExtra("category")
        val amount1 = intent.getStringExtra("amount")
        val date1 = intent.getStringExtra("date")
        transactionType = intent.getIntExtra("type", 1)

        if (transactionType == 1) {
            binding.incomeRadio.isChecked = true
        } else {
            binding.expenseRadio.isChecked = true
        }
        binding.dateTxt.text = date1
        binding.traAmount.setText(amount1)
        binding.txtTraCategory.setText(category1)
        binding.txtTitle.setText(title1)
    }
}