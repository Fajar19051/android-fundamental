package com.android.submission3.ui.home
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.submission3.data.models.User
import com.android.submission3.data.network.ApiConfig
import com.android.submission3.data.network.response.SearchUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(application: Application) : ViewModel() {

    private val _listUser = MutableLiveData<List<User>>()
    val listUser: LiveData<List<User>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    fun searchUser(username: String) {
        _isLoading.value = true
        _status.value = ""
        val client = ApiConfig.getApiService().searchUser(username)
        client.enqueue(object : Callback<SearchUserResponse> {
            override fun onResponse(
                call: Call<SearchUserResponse>,
                response: Response<SearchUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseUsers = response.body()?.items
                    val convertedUsers = ArrayList<User>()

                    responseUsers?.forEach {
                        convertedUsers.add(User(it.login, it.avatarUrl))
                    }
                    _status.value = ""
                    _listUser.value = convertedUsers
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                _isLoading.value = false
                _status.value = "Terjadi kesalahan. Mohon coba beberapa saat lagi"
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}