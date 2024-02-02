package com.example.pagkain_mvvm.activities.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pagkain_mvvm.databinding.CategoryItemsBinding
import com.example.pagkain_mvvm.models.category.CategoryList

class CategoryAdapter() : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    lateinit var onItemClick: ((CategoryList) -> Unit)

    private var categoryList = ArrayList<CategoryList>()

    fun setCategory(categoryList: ArrayList<CategoryList>) {
        this.categoryList = categoryList
        notifyDataSetChanged()
    }

    class CategoryViewHolder(val binding: CategoryItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            CategoryItemsBinding.inflate(
                LayoutInflater.from(
                    parent.context,
                )
            )
        )
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        Glide.with(holder.itemView).load(categoryList[position].strCategoryThumb)
            .into(holder.binding.imgCategory)

        holder.binding.tvCategoryName.text = categoryList[position].strCategory

        holder.binding.tvCategoryDesc.text = categoryList[position].strCategoryDescription

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoryList[position])
        }
    }
}