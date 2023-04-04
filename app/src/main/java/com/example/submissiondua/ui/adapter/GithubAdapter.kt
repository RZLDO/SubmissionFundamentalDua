package com.example.submissiondua.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissiondua.R
import com.example.submissiondua.data.githubModel.ItemsItem

class GithubAdapter (private val userdata : List<ItemsItem>): RecyclerView.Adapter<GithubAdapter.ViewHolder>(){
    private var onItemClickListener : OnItemClickListener? = null

    fun setOnItemClickListener(listener: (position: Int) -> Unit) {
        onItemClickListener = object : OnItemClickListener{
            override fun onItemClick(position: Int) {
                listener(position)
            }
        }
    }
    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view){
        val imageUsers : ImageView = view.findViewById(R.id.iv_imageUsers)
        val username : TextView = view.findViewById(R.id.tv_username)

        fun bind(listener : OnItemClickListener){
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_github_users, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = userdata.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userdata[position]
        holder.username.text = user.login
        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .placeholder(R.drawable.baseline_account_circle_24)
            .into(holder.imageUsers)

        onItemClickListener?.let { listener->
            holder.bind(listener)
        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}