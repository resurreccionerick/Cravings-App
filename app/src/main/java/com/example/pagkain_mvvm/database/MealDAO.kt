package com.example.pagkain_mvvm.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pagkain_mvvm.models.random.MealsItem

@Dao
interface MealDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMeal(meal: MealsItem)

    @Delete
    suspend fun delete(meal: MealsItem)

    @Query("SELECT * FROM mealInformation")
    fun getAllMeal(): LiveData<List<MealsItem>>

}