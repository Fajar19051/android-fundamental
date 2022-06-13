package com.android.submission3.ui.userdetail

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.viewpager2.widget.ViewPager2
import com.android.submission3.R
import com.android.submission3.data.database.Favorites
import com.android.submission3.databinding.FragmentUserDetailBinding
import com.android.submission3.data.models.User
import com.android.submission3.data.models.UserDetail
import com.android.submission3.ui.main.MainActivity
import com.android.submission3.ui.userdetail.adapter.SectionsPagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import de.hdodenhof.circleimageview.CircleImageView

class UserDetailFragment : Fragment() {
    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding
    private val viewModel: UserDetailViewModel by activityViewModels {
        UserDetailViewModelFactory(
            requireActivity().application
        )
    }
    private lateinit var user: User
    private var isFavorite = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        val sectionsPagerAdapter = SectionsPagerAdapter(requireActivity())
        val viewPager: ViewPager2 = binding?.viewPager as ViewPager2
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding?.tabLayout as TabLayout
        TabLayoutMediator(tabs, viewPager){
            tab,
            position -> tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        user = UserDetailFragmentArgs.fromBundle(arguments as Bundle).user

        (activity as MainActivity).setActionBarTitle(user.login)

        viewModel.getUserDetail(user.login)

        viewModel.userDetail.observe(viewLifecycleOwner) {
            setUserDetailData(it)
        }

        viewModel.getFollowers(user.login)
        viewModel.getFollowing(user.login)

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.status.observe(viewLifecycleOwner) {
            showToast(it)
        }

        Glide.with(this)
            .load(user.avatarUrl)
            .apply(RequestOptions().override(550, 550))
            .into(binding?.imgAvatar as CircleImageView)

        binding?.tvUsername?.text = user.login
    }

    private fun setUserDetailData(userData: UserDetail) {
        Glide.with(this)
            .load(userData.avatarUrl)
            .apply(RequestOptions().override(550, 550))
            .into(binding?.imgAvatar as CircleImageView)

        binding?.tvUsername?.text = userData.login
        binding?.tvDetails?.text = resources.getString(
            R.string.dummy_details,
            listOfNotNull(
                userData.name,
                userData.company,
                userData.location
            ).joinToString(separator = " | ")
        )
        binding?.tvFollowerCount?.text = userData.followers.toString()
        binding?.tvFollowingCount?.text = userData.following.toString()
        binding?.tvRepoCount?.text = userData.publicRepos.toString()
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding?.imgAvatar?.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding?.tvUsername?.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding?.tvDetails?.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding?.layoutFollowers?.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding?.tabLayout?.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.user_detail_menu, menu)

        viewModel.checkIfExist(user.login).observe(viewLifecycleOwner) { isExist ->
            isFavorite = if (isExist != null) {
                menu.findItem(R.id.menu_favorite).setIcon(R.drawable.ic_favorited)
                true
            } else {
                menu.findItem(R.id.menu_favorite).setIcon(R.drawable.ic_favorites_border)
                false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favorite -> {
                if (isFavorite) {
                    viewModel.removeFromFavorites(user.login)
                    showToast(getString(R.string.removed))
                } else {
                    viewModel.insertToFavorites(
                        Favorites(
                            login = user.login,
                            avatarUrl = user.avatarUrl
                        )
                    )
                    showToast(getString(R.string.added))
                }
                true
            }else -> NavigationUI.onNavDestinationSelected(
                item,
                requireView().findNavController()
            ) || super.onOptionsItemSelected(item)
        }
    }

    private fun showToast(message: String) {
        if(message.isNotEmpty()){
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}