package com.example.pagkain_mvvm.activities.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pagkain_mvvm.databinding.PopularItemsBinding
import com.example.pagkain_mvvm.models.popular.PopularMeal


class PopularAdapter() : RecyclerView.Adapter<PopularAdapter.PopularMealViewHolder>() {

    lateinit var onItemClick: ((PopularMeal) -> Unit)

    private var mealsList = ArrayList<PopularMeal>()

    fun setMeals(mealsList: ArrayList<PopularMeal>) {
        this.mealsList = mealsList
        notifyDataSetChanged()
    }

    class PopularMealViewHolder(val binding: PopularItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(
            PopularItemsBinding.inflate(
                LayoutInflater.from(
                    parent.context,
                )
            )
        )
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        val meal = mealsList[position]

        Glide.with(holder.itemView).load(meal.strMealThumb)
            .into(holder.binding.imgPopularMeal)

        holder.binding.tvCatItem.text = meal.strMeal

        holder.itemView.setOnClickListener {
            onItemClick.invoke(meal)
        }
    }


}