package com.universodoandroid.starwarsjetpack.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.universodoandroid.starwarsjetpack.local.dao.PersonDao
import com.universodoandroid.starwarsjetpack.local.entity.PersonEntity

@Database(entities = [PersonEntity::class], version = 1, exportSchema = false)
internal abstract class AppDatabase : RoomDatabase() {

    internal abstract fun personDao(): PersonDao

    companion object {
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "database"
                ).build()
            }

            return instance as AppDatabase
        }
    }

}