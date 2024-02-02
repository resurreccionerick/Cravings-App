package com.example.pagkain_mvvm.models.popular


import com.google.gson.annotations.SerializedName

data class PopularList(
    @SerializedName("meals")
    val popularMeals: List<PopularMeal>
)