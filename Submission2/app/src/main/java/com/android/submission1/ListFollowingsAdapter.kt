package com.android.submission1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.submission1.data.User
import com.android.submission1.databinding.ItemRowUserBinding
import com.bumptech.glide.Glide

class ListFollowingsAdapter(private val listFollowing: List<User>) :

    RecyclerView.Adapter<ListFollowingsAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: ListUserAdapter.OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: ListUserAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (username, avatarUrl) = listFollowing[position]

        Glide.with(holder.itemView.context)
            .load(avatarUrl)
            .into(holder.binding.imgItemAvatar)
        holder.binding.tvItemUsername.text = username
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listFollowing[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listFollowing.size
    class ViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)
}