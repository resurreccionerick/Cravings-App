package com.example.pagkain_mvvm.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.pagkain_mvvm.activities.CategoryDetailsActivity
import com.example.pagkain_mvvm.activities.FavoritesActivity
import com.example.pagkain_mvvm.activities.adapter.CategoryAdapter
import com.example.pagkain_mvvm.activities.adapter.CategoryDetailsAdapter
import com.example.pagkain_mvvm.activities.adapter.PopularAdapter
import com.example.pagkain_mvvm.databinding.FragmentHomeBinding
import com.example.pagkain_mvvm.models.category.CategoryList
import com.example.pagkain_mvvm.models.popular.PopularMeal
import com.example.pagkain_mvvm.models.random.MealsItem
import com.example.pagkain_mvvm.viewmodel.CategoryViewModel
import com.example.pagkain_mvvm.viewmodel.HomeViewModel
import com.example.pagkain_mvvm.viewmodel.PopularViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var popularViewModel: PopularViewModel
    private lateinit var categoryViewModel: CategoryViewModel

    private lateinit var randomMeal: MealsItem

    private lateinit var popularAdapter: PopularAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categoryDetailsAdapter: CategoryDetailsAdapter

    companion object {
        const val MEAL_ID = "idMeal"
        const val MEAL_NAME = "idName"
        const val MEAL_PIC = "idPic"
        const val CATEGORY_NAME = "catName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel = ViewModelProviders.of(this)[HomeViewModel::class.java]
        popularViewModel = ViewModelProviders.of(this)[PopularViewModel::class.java]
        categoryViewModel = ViewModelProviders.of(this)[CategoryViewModel::class.java]

        //initializing adapter
        popularAdapter = PopularAdapter()
        categoryAdapter = CategoryAdapter()
        categoryDetailsAdapter = CategoryDetailsAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerView()

        homeViewModel.getRandomMeal()
        popularViewModel.getPopularMeal()
        categoryViewModel.getCategory()


        observeRandomMeal()
        observePopularMeal()
        observeCategoryMeal()

        onRandomMealClicked()
        onPopularItemClicked()
        onCategoryItemClicked()


    }

    private fun onRandomMealClicked() {
        binding.imgRandomMeal.setOnClickListener {
            // Check if randomMeal is initialized before accessing it
            if (::randomMeal.isInitialized) {
                val intent = Intent(activity, FavoritesActivity::class.java)
                intent.putExtra(MEAL_ID, randomMeal.idMeal)
                intent.putExtra(MEAL_NAME, randomMeal.strMeal)
                intent.putExtra(MEAL_PIC, randomMeal.strMealThumb)
                startActivity(intent)
            } else {
                // Handle the case where randomMeal is not initialized yet
                Toast.makeText(
                    requireContext(),
                    "Meal information is not available yet.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun onCategoryItemClicked() {
        categoryAdapter.onItemClick = { categoryFood ->
            val intent = Intent(activity, CategoryDetailsActivity::class.java)
            intent.putExtra(CATEGORY_NAME, categoryFood.strCategory)

            startActivity(intent)
        }
    }

    private fun onPopularItemClicked() {
        popularAdapter.onItemClick = { meal ->
            val intent = Intent(activity, FavoritesActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_PIC, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

            adapter = popularAdapter
        }

        binding.recCategory.apply {
            layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false)

            adapter = categoryAdapter
        }
    }


    private fun observeRandomMeal() {
        homeViewModel.observeRandoMeal().observe(
            viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment).load(meal.strMealThumb).into(binding.imgRandomMeal)

            this.randomMeal = meal
        }
    }


    private fun observeCategoryMeal() {
        categoryViewModel.observerCategoryLiveData().observe(
            viewLifecycleOwner
        ) { category ->
            categoryAdapter.setCategory(categoryList = category as ArrayList<CategoryList>)
        }
    }


    private fun observePopularMeal() {
        popularViewModel.observerPopularMealsLiveData().observe(
            viewLifecycleOwner
        ) { meal ->
            popularAdapter.setMeals(mealsList = meal as ArrayList<PopularMeal>)
        }
    }
}