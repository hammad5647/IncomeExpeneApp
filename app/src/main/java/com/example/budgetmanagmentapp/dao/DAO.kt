package com.example.budgetmanagmentapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.budgetmanagmentapp.entity.TraEntity

@Dao
interface DAO {
    @Insert
    fun insertData(transaction: TraEntity)

    @Update
    fun updateData(transaction: TraEntity)

    @Delete
    fun deleteData(transaction: TraEntity)

    @Query("SELECT * FROM `Transaction`")
    fun readData(): MutableList<TraEntity>

    @Query("SELECT SUM(traAmount)FROM `Transaction` WHERE traType = 2")
    fun getExpense():String

    @Query("SELECT SUM(traAmount)FROM `Transaction` WHERE traType = 1")
    fun getIncome():String
}