package com.example.submissiondua.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.submissiondua.R
import com.example.submissiondua.data.githubModel.ItemsItem
import com.example.submissiondua.data.remote.room.database.FavoriteUsers
import com.example.submissiondua.databinding.ActivityDetailUsersBinding
import com.example.submissiondua.databinding.ActivityFavoriteUsersBinding
import com.example.submissiondua.ui.adapter.FavoriteAdapter
import com.example.submissiondua.ui.adapter.GithubAdapter
import com.example.submissiondua.viewModel.FavoriteViewModel
import com.example.submissiondua.viewModel.ViewModelFactory

class FavoriteUsersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteUsersBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()

        supportActionBar?.title = "Favorite Users"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun getData(){
        val favoriteViewModel = obtainViewModel(this@FavoriteUsersActivity)
        favoriteViewModel.getAllFavorite().observe(this){
            setData(it)
        }
    }

    private fun setData(userData : List<FavoriteUsers>){
        binding.rvFavoriteUsers.setHasFixedSize(true)
        binding.rvFavoriteUsers.layoutManager = LinearLayoutManager(this)
        val adapter = FavoriteAdapter(userData)
        adapter.setOnItemClickListener {
            val intent = Intent(this, DetailUsers::class.java)
            intent.putExtra(DetailUsers.USERNAME, userData[it].username)
            startActivity(intent)
        }
        binding.rvFavoriteUsers.adapter =  adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity):FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }
}