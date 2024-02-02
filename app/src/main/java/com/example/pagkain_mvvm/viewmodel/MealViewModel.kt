package com.example.pagkain_mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pagkain_mvvm.models.random.FoodListResponse
import com.example.pagkain_mvvm.models.random.MealsItem
import com.example.pagkain_mvvm.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel() : ViewModel() {

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

}