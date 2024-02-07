package com.example.pagkain_mvvm.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pagkain_mvvm.MainActivity
import com.example.pagkain_mvvm.activities.CategoryDetailsActivity
import com.example.pagkain_mvvm.activities.FavoritesActivity
import com.example.pagkain_mvvm.activities.adapter.FavoritesAdapter
import com.example.pagkain_mvvm.databinding.FragmentFavoritesBinding
import com.example.pagkain_mvvm.models.random.MealsItem
import com.example.pagkain_mvvm.viewmodel.HomeViewModel


class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favAdapter: FavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).homeViewModel

        favAdapter = FavoritesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeFavorites()
        onFavMealClicked()
        prepareFavItemsRecyclerView()
    }

    private fun onFavMealClicked() {
        favAdapter.onItemClick = { favFood ->
            val intent = Intent(activity, FavoritesActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID, favFood.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME, favFood.strMeal)
            intent.putExtra(HomeFragment.MEAL_PIC, favFood.strMealThumb)
            startActivity(intent)
        }
    }

    private fun prepareFavItemsRecyclerView() {
        binding.recFav.apply {
            layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false)

            adapter = favAdapter
        }
    }

    private fun observeFavorites() {
        viewModel.observeFavoritesMealLiveData().observe(viewLifecycleOwner, Observer { meals ->
            meals.forEach {
                favAdapter.setFavorites(categoryList = meals as ArrayList<MealsItem>)
            }
        })
    }

}