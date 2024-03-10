package com.example.pagkain_mvvm.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pagkain_mvvm.MainActivity
import com.example.pagkain_mvvm.R
import com.example.pagkain_mvvm.activities.FavoritesActivity
import com.example.pagkain_mvvm.activities.adapter.MealsAdapter
import com.example.pagkain_mvvm.databinding.FragmentSearchBinding
import com.example.pagkain_mvvm.models.random.MealsItem
import com.example.pagkain_mvvm.viewmodel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchAdapter: MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).homeViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.layoutLoading.visibility = View.VISIBLE

        prepareRecyclerView()

        onSearchMealClicked()

        binding.icSearch.setOnClickListener {
            searchMeal()
        }

        var search: Job? = null
        binding.edSearch.addTextChangedListener { query ->
            search?.cancel()
            search = lifecycleScope.launch {
                delay(300)
                if (query.isNullOrEmpty()) {
                    findNavController().popBackStack() // Navigate back to HomeFragment
                } else {
                    viewModel.searchMeal(query.toString())
                }
            }
        }


        observeSearchMealLiveData()


    }

    private fun onSearchMealClicked() {
        searchAdapter.onItemClick = { favFood ->
            val intent = Intent(activity, FavoritesActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID, favFood.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME, favFood.strMeal)
            intent.putExtra(HomeFragment.MEAL_PIC, favFood.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeSearchMealLiveData() {
        viewModel.observeSearchLiveData().observe(viewLifecycleOwner, Observer { list ->
            searchAdapter.setFavorites(mealsList = list as ArrayList<MealsItem>)

            //searchAdapter.setFavorites(list as ArrayList<MealsItem>)
        })
    }

    private fun searchMeal() {
        var search = binding.edSearch.text.toString()

        if (search.isNotEmpty()) {
            viewModel.searchMeal(search)
        }
    }

    private fun prepareRecyclerView() {
        searchAdapter = MealsAdapter()

        binding.recSearch.apply {
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            adapter = searchAdapter

            binding.layoutLoading.visibility = View.GONE
        }
    }
}