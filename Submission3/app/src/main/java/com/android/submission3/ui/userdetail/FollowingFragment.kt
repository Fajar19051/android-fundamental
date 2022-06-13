package com.android.submission3.ui.userdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.submission3.databinding.FragmentFollowsBinding
import com.android.submission3.data.models.User
import com.android.submission3.ui.userdetail.adapter.ListFollowsAdapter

class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowsBinding? = null
    private val binding get() = _binding
    private val viewModel: UserDetailViewModel by activityViewModels()

    private lateinit var listFollowingAdapter: ListFollowsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFollowsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listFollowingAdapter = ListFollowsAdapter()
        val layoutManager = LinearLayoutManager(activity)
        binding?.rvFollows?.layoutManager = layoutManager
        binding?.rvFollows?.adapter = listFollowingAdapter

        viewModel.listFollowing.observe(viewLifecycleOwner) {
            setFollowingData(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setFollowingData(following: List<User>) {
        listFollowingAdapter.setListFollower(following)
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding?.rvFollows?.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}