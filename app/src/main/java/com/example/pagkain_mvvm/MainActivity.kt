package com.example.pagkain_mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.pagkain_mvvm.database.MealDatabase
import com.example.pagkain_mvvm.databinding.ActivityMainBinding
import com.example.pagkain_mvvm.viewmodel.HomeViewModel
import com.example.pagkain_mvvm.viewmodel.HomeViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val homeViewModel: HomeViewModel by lazy {
        val mealDatabase = MealDatabase.getInstance(this)
        val homeViewModelProviderFactory = HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this, homeViewModelProviderFactory)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navFragmentController = Navigation.findNavController(this, R.id.navFragmentController)

        NavigationUI.setupWithNavController(binding.btmNav, navFragmentController)


    }
}