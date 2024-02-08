package com.example.pagkain_mvvm.activities.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pagkain_mvvm.databinding.FavMealActivityBinding
import com.example.pagkain_mvvm.models.random.MealsItem

class MealsAdapter : RecyclerView.Adapter<MealsAdapter.FavoritesViewHolder>() {

    lateinit var onItemClick: ((MealsItem) -> Unit)
    lateinit var onItemLongClick: ((MealsItem) -> Unit)

    var favoriteList = ArrayList<MealsItem>()

    fun setFavorites(mealsList: ArrayList<MealsItem>) {
        this.favoriteList = mealsList
        notifyDataSetChanged()
        Log.d("Meal search  ", mealsList.toString())
    }

    fun removeItem(position: Int) {
        favoriteList.removeAt(position)
        notifyItemRemoved(position)
    }

    class FavoritesViewHolder(val binding: FavMealActivityBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FavMealActivityBinding.inflate(inflater, parent, false)
        return FavoritesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val currentItem = favoriteList[position]

        Glide.with(holder.itemView).load(currentItem.strMealThumb)
            .into(holder.binding.imgFavMeal)

        holder.binding.tvFavMealName.text = currentItem.strMeal

        holder.itemView.setOnClickListener {
            onItemClick.invoke(currentItem)
        }

        holder.itemView.setOnLongClickListener {
            onItemLongClick.invoke(currentItem)
            true
        }
    }
}
