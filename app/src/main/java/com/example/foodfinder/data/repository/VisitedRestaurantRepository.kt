package com.example.foodfinder.data.repository

import com.example.foodfinder.data.dao.VisitedRestaurantDao
import com.example.foodfinder.data.model.entity.VisitedRestaurant

class VisitedRestaurantRepository(private val restaurantDao: VisitedRestaurantDao) {

    suspend fun insert(restaurant: VisitedRestaurant) {
        restaurantDao.insert(restaurant)
    }

    suspend fun getRestaurantById(id: Long): VisitedRestaurant? {
        return restaurantDao.getRestaurantById(id)
    }

    suspend fun delete(restaurant: VisitedRestaurant) {
        restaurantDao.delete(restaurant)
    }

    suspend fun getAll(): List<VisitedRestaurant> {
        return restaurantDao.getAllRestaurants()
    }
}
