package com.example.submissiondua.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.service.autofill.UserData
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.bumptech.glide.Glide
import com.example.submissiondua.R
import com.example.submissiondua.data.githubModel.FollowResponseItem
import com.example.submissiondua.databinding.FragmentFollowBinding
import com.example.submissiondua.ui.adapter.FollowAdapter
import com.example.submissiondua.ui.main.DetailUsers
import com.example.submissiondua.viewModel.FollowViewModel

class FollowFragment : Fragment() {
    private lateinit var _binding: FragmentFollowBinding
    private val followViewModel by viewModels<FollowViewModel>()
    private var position = 0
    private var username = ""
    companion object {
        const val POSITION = "0"
        const val USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(layoutInflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(POSITION)
            username= it.getString(USERNAME).toString()
        }
        if (position == 1 ){
            getFollow(username)
        }else{
            getFollowing(username)
        }
    }
    private fun getFollowing(username:String){
        followViewModel.users.observe(viewLifecycleOwner){
            setFollow(it)
        }
        followViewModel.isLoading.observe(viewLifecycleOwner){
            isLoading(it)
        }

        followViewModel.getFollowing(username)
    }
    private fun getFollow(username: String) {
        followViewModel.users.observe(viewLifecycleOwner) {
            setFollow(it)
        }
        followViewModel.isLoading.observe(viewLifecycleOwner) {
            isLoading(it)
        }

        followViewModel.getFollow(username)
    }

    private fun setFollow(userData: List<FollowResponseItem>) {
        _binding.rvFollow.setHasFixedSize(true)
        _binding.rvFollow.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = FollowAdapter(userData)
        adapter.setOnItemClickListener {
            val intent = Intent(context, DetailUsers::class.java)
            intent.putExtra(DetailUsers.USERNAME, userData[it].login)
            startActivity(intent)
        }
        _binding.rvFollow.adapter = adapter
    }

    private fun isLoading(bool: Boolean) {
        _binding.progressBarFollow.visibility = if (bool) View.VISIBLE else View.GONE
    }
}