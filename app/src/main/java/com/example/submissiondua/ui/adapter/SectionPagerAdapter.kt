package com.example.submissiondua.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.submissiondua.ui.fragment.FollowFragment

class SectionPagerAdapter (activity: AppCompatActivity,private val username: String): FragmentStateAdapter(activity){
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowFragment.POSITION, position+1)
            putString(FollowFragment.USERNAME, username)
        }
        return fragment
    }


}