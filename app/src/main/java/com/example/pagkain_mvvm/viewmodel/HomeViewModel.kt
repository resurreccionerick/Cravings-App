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

class HomeViewModel(
    private val mealDatabase: MealDatabase
) : ViewModel() {

    private var randomMealLiveData =
        MutableLiveData<MealsItem>() //this will be used in home fragment

    private var favoritesMealLiveData = mealDatabase.dao().getAllMeal() //this is from database

    private var mealDetailsLiveData = MutableLiveData<MealsItem>()

    fun getRandomMeal() {
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<FoodListResponse> {
            override fun onResponse(
                call: Call<FoodListResponse>,
                response: Response<FoodListResponse>
            ) {
                if (response.body() != null) {
                    val randomMeal: MealsItem = response.body()!!.meals[0]

                    randomMealLiveData.value = randomMeal

                } else {
                    return
                }
            }

            override fun onFailure(call: Call<FoodListResponse>, t: Throwable) {
                //Toast.makeText(this@HomeFragment, t.message.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun observeRandoMeal(): LiveData<MealsItem> { //the mutable live data will be observe by this function, because the mutable live data cant be changed.
        return randomMealLiveData
    }

    fun observeFavoritesMealLiveData(): LiveData<List<MealsItem>> {
        return favoritesMealLiveData
    }

    fun observerMealDetailsLiveData(): LiveData<MealsItem> {
        return mealDetailsLiveData
    }

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

    fun deleteMeal(mealsItem: MealsItem) {
        viewModelScope.launch {
            mealDatabase.dao().delete(mealsItem)
        }
    }

    fun insertMeal(mealsItem: MealsItem) {
        viewModelScope.launch {
            mealDatabase.dao().upsertMeal(mealsItem)
        }
    }
}