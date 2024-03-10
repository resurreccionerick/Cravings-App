package com.example.pagkain_mvvm.fragments

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pagkain_mvvm.MainActivity
import com.example.pagkain_mvvm.activities.FavoritesActivity
import com.example.pagkain_mvvm.activities.adapter.MealsAdapter
import com.example.pagkain_mvvm.databinding.FragmentFavoritesBinding
import com.example.pagkain_mvvm.models.random.MealsItem
import com.example.pagkain_mvvm.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favAdapter: MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).homeViewModel

        favAdapter = MealsAdapter()
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
        binding.layoutLoading.visibility = View.VISIBLE

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

        favAdapter.onItemLongClick = { favFood ->
            val position = favAdapter.favoriteList.indexOf(favFood)
            val deletedMeal = favAdapter.favoriteList[position]

            // Show a confirmation dialog before deleting the item
            showDeleteConfirmationDialog(deletedMeal)

            true
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
            if (meals.isEmpty()) {
                // Show a toast message indicating that the list is empty
                binding.tvEmpty.visibility = View.VISIBLE
            } else {
                meals.forEach {
                    favAdapter.setFavorites(mealsList = meals as ArrayList<MealsItem>)
                }
                binding.tvEmpty.visibility = View.GONE
            }
            binding.layoutLoading.visibility = View.GONE

        })
    }

    private fun showDeleteConfirmationDialog(deletedMeal: MealsItem) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Favorite")
            .setMessage("Are you sure you want to delete this favorite?")
            .setPositiveButton("Delete") { dialogInterface: DialogInterface, _: Int ->
                viewModel.deleteMeal(deletedMeal)
                val position = favAdapter.favoriteList.indexOf(deletedMeal)
                favAdapter.removeItem(position)
                Snackbar.make(requireView(), "Favorite deleted", Snackbar.LENGTH_LONG).setAction(
                    "Undo"
                ) {
                    viewModel.insertMeal(deletedMeal)
                    favAdapter.notifyItemInserted(position)
                }.show()
                dialogInterface.dismiss()
            }
            .setNegativeButton("Cancel") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
            .show()
    }
}
