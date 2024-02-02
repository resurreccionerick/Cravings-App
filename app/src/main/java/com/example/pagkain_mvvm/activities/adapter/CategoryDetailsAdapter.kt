package com.example.pagkain_mvvm.activities.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pagkain_mvvm.databinding.CategoryDetailsItemsBinding
import com.example.pagkain_mvvm.models.category.categoryfood.Meal

class CategoryDetailsAdapter() : RecyclerView.Adapter<CategoryDetailsAdapter.CategoryViewHolder>() {

    lateinit var onItemClick: ((Meal) -> Unit)

    private var categoryDetailsList = ArrayList<Meal>()

    fun setCategory(categoryList: ArrayList<Meal>) {
        this.categoryDetailsList = categoryList
        notifyDataSetChanged()
    }

    class CategoryViewHolder(val binding: CategoryDetailsItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            CategoryDetailsItemsBinding.inflate(
                LayoutInflater.from(
                    parent.context,
                )
            )
        )
    }

    override fun getItemCount(): Int {
        return categoryDetailsList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        Glide.with(holder.itemView).load(categoryDetailsList[position].strMealThumb)
            .into(holder.binding.imgCategory)

        holder.binding.tvCategoryName.text = categoryDetailsList[position].strMeal


        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoryDetailsList[position])
        }
    }
}