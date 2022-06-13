package com.android.submission1

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.submission1.data.ApiConfig
import com.android.submission1.data.SearchResponse
import com.android.submission1.data.User
import com.android.submission1.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private lateinit var binding: ActivityMainBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Github User\'s Search"
        binding.rvUsers.setHasFixedSize(true)
        binding.btnSend.setOnClickListener {
            searchUser(binding.tvSearch.text.toString())
        }
    }
    private fun searchUser(q:String){
        val listUser = ArrayList<User>()
        showLoading(true)
        val client = ApiConfig.getApiService().getSearch(q)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ){
                showLoading(false)
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null) {
                        for(user in responseBody.items){
                            listUser.add(
                                User(
                                user.login,
                                user.avatarUrl
                            )
                            )
                        }
                        showRecyclerList(listUser)
                    }
                }
            }
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                showLoading(true)
            }
        })
    }

    private fun showRecyclerList(list: ArrayList<User>) {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvUsers.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvUsers.layoutManager = LinearLayoutManager(this)
        }
        if(list.isEmpty()){
            binding.imgEmpty.visibility = View.VISIBLE
            binding.tvEmptymsg.visibility = View.VISIBLE
            binding.rvUsers.visibility = View.GONE
        }else{
            binding.imgEmpty.visibility = View.GONE
            binding.tvEmptymsg.visibility = View.GONE
            binding.rvUsers.visibility = View.VISIBLE
            val listUserAdapter = ListUserAdapter(list)
            binding.rvUsers.adapter = listUserAdapter

            listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: User) {
                    showSelectedUser(data)
                }
            })
        }
    }
    private fun showSelectedUser(user: User) {
        Toast.makeText(this, "Show ${user.username} Profile", Toast.LENGTH_SHORT).show()
        val moveWithObjectIntent = Intent(this@MainActivity, DetailActivity::class.java)
        moveWithObjectIntent.putExtra(DetailActivity.EXTRA_PERSON, user)
        startActivity(moveWithObjectIntent)
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}