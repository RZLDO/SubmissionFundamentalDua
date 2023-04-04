package com.example.submissiondua.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.submissiondua.R
import com.example.submissiondua.data.githubModel.DetailResponse
import com.example.submissiondua.data.remote.room.database.FavoriteUsers
import com.example.submissiondua.databinding.ActivityDetailUsersBinding
import com.example.submissiondua.ui.adapter.SectionPagerAdapter
import com.example.submissiondua.viewModel.DetailUsersViewModel
import com.example.submissiondua.viewModel.FavoriteUsersViewModel
import com.example.submissiondua.viewModel.FavoriteViewModel
import com.example.submissiondua.viewModel.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUsers : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUsersBinding
    private val detailUserModel by viewModels<DetailUsersViewModel>()
    private lateinit var favoriteUsers : FavoriteUsers

    private lateinit var favoriteUsersViewModel: FavoriteUsersViewModel

    companion object{
        const val USERNAME = ""
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(USERNAME)
        getUserData(username.toString())

        val sectionPagerAdapter = username?.let { SectionPagerAdapter(this,it) }
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionPagerAdapter

        val tabs : TabLayout = findViewById(R.id.tabsLayout)
        TabLayoutMediator(tabs,viewPager){tab, position->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.title = "Detail User"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        favoriteUsersViewModel = obtainViewModel(this@DetailUsers)

        favoriteUsersViewModel.getUsersByUsername(username.toString()).observe(this){
            if (it==null){
                binding.btnFloating.setImageResource(R.drawable.baseline_favorite_border_24)
                binding.btnFloating.setOnClickListener {
                    favoriteUsersViewModel.insert(favoriteUsers)
                }
            }else{
                binding.btnFloating.setImageResource(R.drawable.baseline_favorite_24)
                binding.btnFloating.setOnClickListener {
                    favoriteUsersViewModel.delete(favoriteUsers)
                }
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity) : FavoriteUsersViewModel{
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteUsersViewModel::class.java]
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun getUserData(username:String){
        detailUserModel.detailUsers.observe(this){
            setUserData(it)
        }
        detailUserModel.isLoading.observe(this){
            isLoading(it)
        }
        detailUserModel.getDetail(username)
    }

    private fun setUserData(userData : DetailResponse){
        Glide.with(this)
            .load(userData.avatarUrl)
            .placeholder(R.drawable.baseline_account_circle_24)
            .into(binding.ivImageDetail)

        binding.tvNameDetail.text = userData.name
        binding.tvUsernameDetail.text = userData.login
        "Followers: ${userData.followers}".also { binding.tvFollowers.text = it }
        "Following: ${userData.following}".also { binding.tvFollowing.text = it }

        favoriteUsers = FavoriteUsers(
            userData.id,
            userData.login,
            userData.avatarUrl
        )
    }
    private fun isLoading(bool: Boolean){
        binding.progressBarDetail.visibility = if (bool) View.VISIBLE else View.GONE
    }
}