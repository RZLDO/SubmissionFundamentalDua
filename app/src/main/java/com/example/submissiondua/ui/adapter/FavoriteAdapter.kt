package com.example.submissiondua.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissiondua.R
import com.example.submissiondua.data.remote.room.database.FavoriteUsers

class FavoriteAdapter (private val favoriteUsers: List<FavoriteUsers>):RecyclerView.Adapter<FavoriteAdapter.ViewModel>(){
    private var onItemClickListener : OnItemClickListener? = null
    fun setOnItemClickListener(listener: (position: Int) -> Unit) {
        onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                listener(position)
            }
        }
    }
    inner class ViewModel(view:View):RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.iv_imageFavorite)
        val username : TextView = view.findViewById(R.id.tv_usernameFavorite)
        fun bind(listener : OnItemClickListener){
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ViewModel {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_favorite, parent, false)
        return ViewModel(view)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.ViewModel, position: Int) {
        val userData = favoriteUsers[position]
        Glide.with(holder.itemView.context)
            .load(userData.avatarUrl)
            .placeholder(R.drawable.baseline_account_circle_24)
            .into(holder.image)

        holder.username.text = userData.username

        onItemClickListener?.let { listener->
            holder.bind(listener)
        }
    }

    override fun getItemCount(): Int = favoriteUsers.size

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}