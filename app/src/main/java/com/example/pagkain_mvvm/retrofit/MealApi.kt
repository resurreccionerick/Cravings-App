package com.example.pagkain_mvvm.retrofit

import com.example.pagkain_mvvm.models.category.Category
import com.example.pagkain_mvvm.models.category.CategoryList
import com.example.pagkain_mvvm.models.category.categoryfood.CategoryFood
import com.example.pagkain_mvvm.models.popular.PopularList
import com.example.pagkain_mvvm.models.random.FoodListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal(): Call<FoodListResponse>

    @GET("lookup.php?")
    fun getRandomMealInfo(@Query("i") id: String): Call<FoodListResponse>

    @GET("filter.php?")
    fun getPopularInfo(@Query("c") popularName: String): Call<PopularList>

    @GET("categories.php")
    fun getCategory(): Call<Category>

    @GET("filter.php?")
    fun getCategoryInfo(@Query("c") id: String): Call<CategoryFood>

    @GET("search.php")
    fun searchItem(@Query("s") searchQuery: String): Call<FoodListResponse>
}