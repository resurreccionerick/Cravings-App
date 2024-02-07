package com.example.pagkain_mvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pagkain_mvvm.database.MealDatabase

class MealViewModelFactory(private val mealDatabase: MealDatabase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MealViewModel(mealDatabase) as T
    }
}
