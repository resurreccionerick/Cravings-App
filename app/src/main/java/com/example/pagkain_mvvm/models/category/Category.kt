package com.example.pagkain_mvvm.models.category


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("categories")
    val categories: List<CategoryList>
)