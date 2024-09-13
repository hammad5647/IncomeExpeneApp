package com.example.budgetmanagmentapp.helper

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.budgetmanagmentapp.dao.DAO
import com.example.budgetmanagmentapp.entity.TraEntity

@Database(entities = [TraEntity::class], version = 1)
abstract class DBHelper : RoomDatabase() {

    abstract fun dao(): DAO

    companion object {
        var db: DBHelper? = null
        fun initDatabase(context: Context): DBHelper {
            if (db == null) {
                db = Room.databaseBuilder(context, DBHelper::class.java, "dataBase")
                    .allowMainThreadQueries().build()
            }
            return db!!
        }
    }
}