package com.example.pagkain_mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pagkain_mvvm.models.category.Category
import com.example.pagkain_mvvm.models.category.CategoryList
import com.example.pagkain_mvvm.models.category.categoryfood.CategoryFood
import com.example.pagkain_mvvm.models.category.categoryfood.Meal
import com.example.pagkain_mvvm.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel() : ViewModel() {

    private var categoryMealLiveData =
        MutableLiveData<List<CategoryList>>() //this will be used in home fragment

    private var categoryDetailsLiveData =
        MutableLiveData<List<Meal>>() //this will be used in home fragment



    fun getCategory() {
        RetrofitInstance.api.getCategory().enqueue(object : Callback<Category> {
            override fun onResponse(call: Call<Category>, response: Response<Category>) {
                response.body()?.let { category ->
                    categoryMealLiveData.postValue(category.categories)
                }

            }

            override fun onFailure(call: Call<Category>, t: Throwable) {
                Log.d("category vm error: ", t.message.toString())
            }
        })
    }

    fun getCategoryDetails(id: String) {
        RetrofitInstance.api.getCategoryInfo(id).enqueue(object : Callback<CategoryFood> {

            override fun onResponse(
                call: Call<CategoryFood>,
                response: Response<CategoryFood>
            ) {
                response.body()?.let { category ->
                    categoryDetailsLiveData.postValue(category.meals)
                    Log.d("cate vm succcess: ", category.toString()) //check kung papasok pag bukas mo check mo to ha
                }
            }

            override fun onFailure(call: Call<CategoryFood>, t: Throwable) {
                Log.d("cate vm error: ", t.message.toString())
            }
        })
    }

    fun observerCategoryLiveData(): LiveData<List<CategoryList>> {
        return categoryMealLiveData
    }

    fun observerCategoryDetailsLiveData(): LiveData<List<Meal>> {
        return categoryDetailsLiveData
    }
}