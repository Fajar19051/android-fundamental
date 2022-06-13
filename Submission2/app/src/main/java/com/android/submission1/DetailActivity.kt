package com.android.submission1

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.android.submission1.data.ApiConfig
import com.android.submission1.data.DetailsResponse
import com.android.submission1.data.User
import com.android.submission1.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: UserDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Detail User\'s"
        val user = intent.getParcelableExtra<User>(EXTRA_PERSON) as User
        showdetail(user.username)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) {
                tab, position -> tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
        viewModel.getFollowers(user.username)
        viewModel.getFollowing(user.username)
    }
    private fun showdetail(uname:String?){
        showLoading(true)
        val client = ApiConfig.getApiService().getUser(uname)
        client.enqueue(object : Callback<DetailsResponse> {
            override fun onResponse(
                call: Call<DetailsResponse>,
                response: Response<DetailsResponse>
            ){
                showLoading(false)
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Glide.with(binding.imgAvatar.context).load(responseBody.avatarUrl).into(binding.imgAvatar)
                        binding.tvName.text = responseBody.name
                        val userName = "(@${responseBody.login})"
                        binding.tvUsername.text = userName
                        binding.tvFollowerCount.text = responseBody.followers.toString()
                        binding.tvFollowingCount.text = responseBody.following.toString()
                        binding.tvRepoCount.text = responseBody.publicRepos.toString()
                    }
                }
            }
            override fun onFailure(call: Call<DetailsResponse>, t: Throwable) {
                showLoading(false)
            }
        })
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
    companion object {
        const val EXTRA_PERSON = "extra_person"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}