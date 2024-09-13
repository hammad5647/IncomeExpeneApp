package com.example.budgetmanagmentapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Transaction")
data class TraEntity(
    @PrimaryKey(true)
    var traId: Int = 0,
    @ColumnInfo
    var traTitle:String,
    @ColumnInfo
    var traCategory: String,
    @ColumnInfo
    var traDate : String,
    @ColumnInfo
    var traAmount: String,
    @ColumnInfo
    var traType: Int = 0
)