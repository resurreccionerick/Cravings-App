package com.example.pagkain_mvvm.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.pagkain_mvvm.databinding.ActivityFavoritesBinding
import com.example.pagkain_mvvm.fragments.HomeFragment
import com.example.pagkain_mvvm.viewmodel.MealViewModel

class FavoritesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritesBinding
    private lateinit var mealName: String
    private lateinit var mealId: String
    private lateinit var ytLink: String
    private lateinit var mealPic: String
    private lateinit var viewmodel: MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewmodel = ViewModelProviders.of(this)[MealViewModel::class.java]

        getMealInformation() //get from home fragment

        setInformation() //after getMealInfo... then set info into views

        loadingCase()
        viewmodel.getMealDetails(mealId)
        observerMealDetailsLiveData()

    }

    private fun observerMealDetailsLiveData() {
        viewmodel.observerMealDetailsLiveData().observe(
            this
        ) { value ->

            binding.tvCategoryInfo.text = "Category " + value.strCategory
            binding.tvAreaInfo.text = "Area: " + value.strArea
            binding.tvContent.text = value.strInstructions
            binding.tvLink.text = value.strSource
            ytLink = value.strYoutube.toString()

            binding.btnYoutube.setOnClickListener {
                openLink(value.strYoutube.toString())
            }

            binding.tvLink.setOnClickListener {
                openLink(value.strSource.toString())
            }

            afterLoading()
        }
    }

    private fun getMealInformation() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealPic = intent.getStringExtra(HomeFragment.MEAL_PIC)!!
    }

    private fun setInformation() {
        Glide.with(this@FavoritesActivity).load(mealPic).into(binding.imgMealDetail)

        binding.collapseToolbar.title = mealName
    }

    private fun loadingCase() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnSave.visibility = View.INVISIBLE
        binding.btnYoutube.visibility = View.INVISIBLE
        binding.tvContent.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvAreaInfo.visibility = View.INVISIBLE
        binding.tvContent.visibility = View.INVISIBLE
        binding.tvCategoryInfo.visibility = View.INVISIBLE
    }

    private fun afterLoading() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnSave.visibility = View.VISIBLE
        binding.btnYoutube.visibility = View.VISIBLE
        binding.tvContent.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvAreaInfo.visibility = View.VISIBLE
        binding.tvLink.visibility = View.VISIBLE
        binding.tvCategoryInfo.visibility = View.VISIBLE
    }

    private fun openLink(link: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(intent)
    }
}