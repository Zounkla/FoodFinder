package com.example.foodfinder.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodfinder.data.model.entity.VisitedRestaurant

@Dao
interface VisitedRestaurantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(restaurant: VisitedRestaurant)

    @Query("SELECT * FROM visitedrestaurant")
    suspend fun getAllRestaurants(): List<VisitedRestaurant>

    @Query("SELECT * FROM visitedrestaurant WHERE id = :id")
    suspend fun getRestaurantById(id: Long): VisitedRestaurant?

    @Delete
    suspend fun delete(restaurant: VisitedRestaurant)
}
