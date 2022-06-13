package com.android.submission3.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.submission3.data.database.Favorites
import com.android.submission3.databinding.FragmentFavoritesBinding
import com.android.submission3.data.models.User
import com.android.submission3.ui.favorites.adapter.ListFavoritesAdapter

class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding
    private val viewModel: FavoritesViewModel by activityViewModels { FavoritesViewModelFactory(requireActivity().application) }

    private lateinit var listFavoritesAdapter: ListFavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listFavoritesAdapter = ListFavoritesAdapter()
        binding?.rvFavorite?.layoutManager = LinearLayoutManager(activity)
        binding?.rvFavorite?.setHasFixedSize(true)
        binding?.rvFavorite?.adapter = listFavoritesAdapter

        viewModel.getAllFavorites().observe(viewLifecycleOwner) {
            setFavoriteListData(it)
        }
    }

    private fun setFavoriteListData(favoriteList: List<Favorites>) {
        if (favoriteList.isNotEmpty()) {
            binding?.tvPlaceholder?.visibility = View.GONE
            binding?.rvFavorite?.visibility = View.VISIBLE
        } else {
            binding?.tvPlaceholder?.visibility = View.VISIBLE
            binding?.rvFavorite?.visibility = View.VISIBLE
        }

        listFavoritesAdapter.setListFavorites(favoriteList)
        listFavoritesAdapter.setOnItemClickCallback(object : ListFavoritesAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val toUserDetailFragment =
                    FavoritesFragmentDirections.actionFavoriteFragmentToUserDetailFragment(data)
                view?.findNavController()?.navigate(toUserDetailFragment)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}