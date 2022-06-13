package com.android.submission3.ui.favorites.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.submission3.data.database.Favorites
import com.android.submission3.data.models.User
import com.android.submission3.databinding.ItemRowUserBinding
import com.android.submission3.widget.FavoritesDiffCallback
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ListFavoritesAdapter : RecyclerView.Adapter<ListFavoritesAdapter.ViewHolder>() {
    private val listFavorites = ArrayList<Favorites>()
    fun setListFavorites(listFavorites: List<Favorites>) {
        val diffCallback = FavoritesDiffCallback(this.listFavorites, listFavorites)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorites.clear()
        this.listFavorites.addAll(listFavorites)
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
        val (_, login, avatarUrl) = listFavorites[position]

        Glide.with(holder.itemView.context)
            .load(avatarUrl)
            .apply(RequestOptions().override(550, 550))
            .into(holder.binding.imgItemPhoto)

        holder.binding.tvItemUsername.text = login
        holder.binding.ivFavorited.visibility = View.VISIBLE
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(
                User(
                    login = listFavorites[holder.adapterPosition].login,
                    avatarUrl = listFavorites[holder.adapterPosition].avatarUrl
                )
            )
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun getItemCount(): Int = listFavorites.size
}