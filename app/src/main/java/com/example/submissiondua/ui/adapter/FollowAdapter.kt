package com.example.submissiondua.ui.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissiondua.R
import com.example.submissiondua.data.githubModel.DetailResponse
import com.example.submissiondua.data.githubModel.FollowResponse
import com.example.submissiondua.data.githubModel.FollowResponseItem

class FollowAdapter(private val followData : List<FollowResponseItem>) : RecyclerView.Adapter<FollowAdapter.ViewModel>() {
    private var onItemClickListener : OnItemClickListener? = null

    fun setOnItemClickListener(listener: (Position:Int)-> Unit){
        onItemClickListener = object : OnItemClickListener{
            override fun onItemClick(position: Int) {
                listener(position)
            }
        }
    }

    inner class ViewModel (view: View): RecyclerView.ViewHolder(view){
        val imageFollow : ImageView = view.findViewById(R.id.iv_follow)
        val usernameFollow: TextView = view.findViewById(R.id.tv_username_follow)

        fun bind(listener:OnItemClickListener){
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewModel {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_follow, parent, false)

        return ViewModel(view)
    }

    override fun getItemCount(): Int = followData.size

    override fun onBindViewHolder(holder: ViewModel, position: Int) {
        val userData = followData[position]
        Glide.with(holder.itemView.context)
            .load(userData.avatarUrl)
            .placeholder(R.drawable.baseline_account_circle_24)
            .into(holder.imageFollow)
        holder.usernameFollow.text = userData.login

        onItemClickListener?.let {
            holder.bind(it)
        }

    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}