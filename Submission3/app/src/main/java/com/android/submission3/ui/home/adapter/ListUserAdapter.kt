package com.android.submission3.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.android.submission3.databinding.ItemRowUserBinding
import com.android.submission3.data.models.User
import com.android.submission3.widget.UserDiffCallback

class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {
    private val listUser = ArrayList<User>()
    fun setListUser(listUser: List<User>) {
        val diffCallback = UserDiffCallback(this.listUser, listUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listUser.clear()
        this.listUser.addAll(listUser)
        diffResult.dispatchUpdatesTo(this)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

    class ViewHolder(val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (login, avatarUrl, isFavorited) = listUser[position]

        Glide.with(holder.itemView.context)
            .load(avatarUrl)
            .apply(RequestOptions().override(550, 550))
            .into(holder.binding.imgItemPhoto)

        holder.binding.tvItemUsername.text = login
        holder.binding.ivFavorited.visibility = if(isFavorited) View.VISIBLE else View.GONE
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[holder.adapterPosition]) }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun getItemCount(): Int = listUser.size
}