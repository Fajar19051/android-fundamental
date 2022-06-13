package com.android.submission1

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.submission1.data.User
import com.android.submission1.databinding.FragmentFollowingsBinding

class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingsBinding.inflate(layoutInflater, container, false)

        val layoutManager = LinearLayoutManager(activity)
        binding.rvFollowing.layoutManager = layoutManager
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listFollowing.observe(viewLifecycleOwner) {
            setFollowingData(it)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.rvFollowing.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.rvFollowing.visibility = View.VISIBLE
        }
    }
    private fun showSelectedUser(user: User) {
        Toast.makeText(activity, "Show ${user.username} Profile", Toast.LENGTH_SHORT).show()
        val moveWithObjectIntent = Intent(activity, DetailActivity::class.java)
        moveWithObjectIntent.putExtra(DetailActivity.EXTRA_PERSON, user)
        startActivity(moveWithObjectIntent)
    }
    private fun setFollowingData(following: List<User>) {
        val listFollowingAdapter = ListFollowingsAdapter(following)
        binding.rvFollowing.adapter = listFollowingAdapter
        listFollowingAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showSelectedUser(data)
            }
        })
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}