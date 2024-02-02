package com.example.pagkain_mvvm.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.pagkain_mvvm.R
import com.example.pagkain_mvvm.activities.adapter.CategoryDetailsAdapter
import com.example.pagkain_mvvm.databinding.ActivityCategoryDetailsBinding
import com.example.pagkain_mvvm.fragments.HomeFragment
import com.example.pagkain_mvvm.models.category.categoryfood.Meal
import com.example.pagkain_mvvm.viewmodel.CategoryViewModel
import com.example.pagkain_mvvm.viewmodel.MealViewModel

class CategoryDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryDetailsBinding
    private lateinit var categoryName: String
    private lateinit var viewmodel: CategoryViewModel
    private lateinit var categoryDetailsAdapter: CategoryDetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCategoryDetailsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewmodel = ViewModelProviders.of(this)[CategoryViewModel::class.java]

        // Initialize categoryDetailsAdapter here
        categoryDetailsAdapter = CategoryDetailsAdapter()

        prepareRecyclerView()
        onCategoryItemClicked()

        getCategoryInformation() //get from home fragment
    }

    private fun prepareRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(
                this@CategoryDetailsActivity,
                3, GridLayoutManager.VERTICAL,
                false
            )

            adapter = categoryDetailsAdapter
        }
    }

    private fun onCategoryItemClicked() {
        categoryDetailsAdapter.onItemClick = { categoryFood ->
            val intent = Intent(CategoryDetailsActivity@ this, FavoritesActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID, categoryFood.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME, categoryFood.strMeal)
            intent.putExtra(HomeFragment.MEAL_PIC, categoryFood.strMealThumb)
            startActivity(intent)
        }
    }


    private fun getCategoryInformation() {
        val intent = intent
        categoryName = intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!
        Log.d("cat name", categoryName)

        viewmodel.getCategoryDetails(categoryName)
        observeCategoryDetails()
    }

    private fun observeCategoryDetails() {
        viewmodel.observerCategoryDetailsLiveData().observe(
            this
        ) { category ->
            category?.let { // Check if category is not null
                categoryDetailsAdapter.setCategory(categoryList = it as ArrayList<Meal>)

                binding.tvSize.text = "Total Menu: " + category.size.toString()
            }
        }
    }
}