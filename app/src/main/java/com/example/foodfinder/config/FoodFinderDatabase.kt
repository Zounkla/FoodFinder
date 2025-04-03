package com.example.foodfinder.config

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodfinder.data.dao.VisitedRestaurantDao

import com.example.foodfinder.data.model.entity.VisitedRestaurant

@Database(entities = [VisitedRestaurant::class], version = 1, exportSchema = false)
abstract  class FoodFinderDatabase : RoomDatabase() {

    abstract fun visitedRestaurantDao(): VisitedRestaurantDao

    companion object {
        @Volatile
        private var INSTANCE: FoodFinderDatabase? = null

        fun getDatabase(context: Context): FoodFinderDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FoodFinderDatabase::class.java,
                    "foodfinder_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}