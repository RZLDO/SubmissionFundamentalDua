package com.example.submissiondua.ui.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.submissiondua.R
import com.example.submissiondua.data.dataStore.SettingPreferences
import com.example.submissiondua.databinding.ActivitySettingBinding
import com.example.submissiondua.viewModel.SettingViewModel
import com.example.submissiondua.viewModel.ViewModelSettingFactory

class SettingActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySettingBinding
    private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "Setting")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(this, ViewModelSettingFactory(pref))[SettingViewModel::class.java]
        supportActionBar?.title = "Setting Theme"


        binding.switchTheme.setOnCheckedChangeListener{_:CompoundButton?,isChecked: Boolean->
            if (isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
            settingViewModel.saveThemeSetting(isChecked)
        }
    }
}