package com.android.submission3.ui.userdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.submission3.databinding.ItemRowUserBinding
import com.android.submission3.data.models.User
import com.android.submission3.widget.UserDiffCallback
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ListFollowsAdapter : RecyclerView.Adapter<ListFollowsAdapter.ViewHolder>() {
    private val listFollower = ArrayList<User>()
    fun setListFollower(listFollower: List<User>) {
        val diffCallback = UserDiffCallback(this.listFollower, listFollower)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFollower.clear()
        this.listFollower.addAll(listFollower)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (login, avatarUrl) = listFollower[position]

        Glide.with(holder.itemView.context)
            .load(avatarUrl)
            .apply(RequestOptions().override(550, 550))
            .into(holder.binding.imgItemPhoto)

        holder.binding.tvItemUsername.text = login
    }

    override fun getItemCount(): Int = listFollower.size
}