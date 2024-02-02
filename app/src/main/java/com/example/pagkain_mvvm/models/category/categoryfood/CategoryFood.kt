package com.example.pagkain_mvvm.models.category.categoryfood


import com.google.gson.annotations.SerializedName

data class CategoryFood(
    @SerializedName("meals")
    val meals: List<Meal>
)