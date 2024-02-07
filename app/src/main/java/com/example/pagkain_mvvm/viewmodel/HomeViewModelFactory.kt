package com.example.pagkain_mvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pagkain_mvvm.database.MealDatabase

class HomeViewModelFactory(private val mealDatabase: MealDatabase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(mealDatabase) as T
    }
}
