package com.example.pagkain_mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pagkain_mvvm.models.popular.PopularList
import com.example.pagkain_mvvm.models.popular.PopularMeal
import com.example.pagkain_mvvm.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularViewModel() : ViewModel() {

    private var popularMealLiveData = MutableLiveData<List<PopularMeal>>()

    fun getPopularMeal() {
        RetrofitInstance.api.getPopularInfo("Dessert").enqueue(object : Callback<PopularList> {
            override fun onResponse(
                call: Call<PopularList>,
                response: Response<PopularList>
            ) {
                if (response.body() != null) {
                    popularMealLiveData.value = response.body()!!.popularMeals
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<PopularList>, t: Throwable) {
                Log.d("Meal vm error: ", t.message.toString())
            }

        })
    }

    fun observerPopularMealsLiveData(): LiveData<List<PopularMeal>> {
        return popularMealLiveData
    }
}