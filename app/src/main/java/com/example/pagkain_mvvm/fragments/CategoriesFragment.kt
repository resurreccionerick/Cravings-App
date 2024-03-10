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
import com.example.pagkain_mvvm.activities.CategoryDetailsActivity
import com.example.pagkain_mvvm.activities.FavoritesActivity
import com.example.pagkain_mvvm.activities.adapter.CategoryAdapter
import com.example.pagkain_mvvm.databinding.FragmentCategoriesBinding
import com.example.pagkain_mvvm.models.category.CategoryList
import com.example.pagkain_mvvm.viewmodel.CategoryViewModel

class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        categoryViewModel = ViewModelProviders.of(this)[CategoryViewModel::class.java]
        categoryAdapter = CategoryAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.layoutLoading.visibility = View.VISIBLE

        categoryViewModel.getCategory()
        observeCategoryMeal()
        onCategoryItemClicked()
        preparePopularItemsRecyclerView()
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recCategory.apply {
            layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false)

            adapter = categoryAdapter
        }
    }

    private fun observeCategoryMeal() {
        categoryViewModel.observerCategoryLiveData().observe(
            viewLifecycleOwner
        ) { category ->
            categoryAdapter.setCategory(categoryList = category as ArrayList<CategoryList>)

            binding.layoutLoading.visibility = View.GONE
        }
    }


    private fun onCategoryItemClicked() {
        categoryAdapter.onItemClick = { categoryFood ->
            val intent = Intent(activity, CategoryDetailsActivity::class.java)
            intent.putExtra(HomeFragment.CATEGORY_NAME, categoryFood.strCategory)

            startActivity(intent)
        }
    }
}