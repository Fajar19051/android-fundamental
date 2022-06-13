package com.android.submission1

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.submission1.data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel : ViewModel() {

    private val _listFollower = MutableLiveData<List<User>>()
    val listFollower: LiveData<List<User>> = _listFollower

    private val _listFollowing = MutableLiveData<List<User>>()
    val listFollowing: LiveData<List<User>> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollowers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object: Callback<List<FollowerResponse>>{
            override fun onResponse(
                call: Call<List<FollowerResponse>>,
                response: Response<List<FollowerResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseFollowers = response.body()
                    val convertedFollowers = ArrayList<User>()

                    responseFollowers?.forEach {
                        convertedFollowers.add(User(it.login, it.avatarUrl))
                    }
                    _listFollower.postValue(convertedFollowers)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<FollowerResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object: Callback<List<FollowingResponse>>{
            override fun onResponse(
                call: Call<List<FollowingResponse>>,
                response: Response<List<FollowingResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseFollowing = response.body()
                    val convertedFollowing = ArrayList<User>()

                    responseFollowing?.forEach {
                        convertedFollowing.add(User(it.login, it.avatarUrl))
                    }
                    _listFollowing.postValue(convertedFollowing)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<FollowingResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
    companion object {
        private const val TAG = "UserDetailViewModel"
    }
}