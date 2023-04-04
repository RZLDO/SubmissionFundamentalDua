package com.example.submissiondua.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissiondua.R
import com.example.submissiondua.data.dataStore.SettingPreferences
import com.example.submissiondua.data.githubModel.ItemsItem
import com.example.submissiondua.databinding.ActivityMainBinding
import com.example.submissiondua.ui.adapter.GithubAdapter
import com.example.submissiondua.viewModel.MainActivityViewModel
import com.example.submissiondua.viewModel.SettingViewModel
import com.example.submissiondua.viewModel.ViewModelSettingFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainActivityViewModel>()
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name="Setting")

    companion object{
        private const val USERNAME = "Rizki"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Github Users"
        getData(USERNAME)

        val pref = SettingPreferences.getInstance(dataStore)

        val settingViewModel = ViewModelProvider(this,ViewModelSettingFactory(pref)).get(SettingViewModel::class.java)
        settingViewModel.getThemeSetting().observe(this) {
            if(it){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favourite -> {
                val intent = Intent(this, FavoriteUsersActivity::class.java)
                startActivity(intent)
            }
            R.id.setting->{
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)

        searchView.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                getData(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        }
        )
        return super.onCreateOptionsMenu(menu)
    }
    private fun getData(username:String){
        mainViewModel.users.observe(this){
            getUserData(it)
        }
        mainViewModel.isLoading.observe(this){
            isLoading(it)
        }
        mainViewModel.getUsers(username)
    }

    private fun getUserData(githubUsers: List<ItemsItem>){
        binding.rvGithubUsers.setHasFixedSize(true)
        binding.rvGithubUsers.layoutManager = LinearLayoutManager(this)
        val adapter = GithubAdapter(githubUsers)
        adapter.setOnItemClickListener { items->
            val intent = Intent(this, DetailUsers::class.java)
            intent.putExtra(DetailUsers.USERNAME,githubUsers[items].login)
            startActivity(intent)
        }
        binding.rvGithubUsers.adapter = adapter
    }

    private fun isLoading(bool : Boolean){
        binding.progressBar.visibility = if (bool) View.VISIBLE else View.GONE
    }
}