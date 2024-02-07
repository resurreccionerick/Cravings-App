package com.example.pagkain_mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pagkain_mvvm.database.MealDatabase
import com.example.pagkain_mvvm.models.random.FoodListResponse
import com.example.pagkain_mvvm.models.random.MealsItem
import com.example.pagkain_mvvm.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(val mealDatabase: MealDatabase) : ViewModel() {

    private var mealDetailsLiveData = MutableLiveData<MealsItem>()

    fun getMealDetails(id: String) {
        RetrofitInstance.api.getRandomMealInfo(id).enqueue(object : Callback<FoodListResponse> {

            override fun onResponse(
                call: Call<FoodListResponse>,
                response: Response<FoodListResponse>
            ) {
                if (response.body() != null) {
                    mealDetailsLiveData.value = response.body()!!.meals[0]
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<FoodListResponse>, t: Throwable) {
                Log.d("Meal vm error: ", t.message.toString())
            }
        })
    }

    fun observerMealDetailsLiveData(): LiveData<MealsItem> {
        return mealDetailsLiveData
    }

    fun insertMeal(mealsItem: MealsItem) {
        viewModelScope.launch {
            mealDatabase.dao().upsertMeal(mealsItem)
        }
    }

    fun deleteMeal(mealsItem: MealsItem) {
        viewModelScope.launch {
            mealDatabase.dao().delete(mealsItem)
        }
    }

}