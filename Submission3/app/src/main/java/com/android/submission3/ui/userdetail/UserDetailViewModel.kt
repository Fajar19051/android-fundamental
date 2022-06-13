package com.android.submission3.ui.userdetail
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.submission3.data.database.Favorites
import com.android.submission3.data.network.ApiConfig
import com.android.submission3.data.network.response.UserFollowResponse
import com.android.submission3.data.network.response.UserDetailResponse
import com.android.submission3.data.repository.FavoriteRepository
import com.android.submission3.data.models.User
import com.android.submission3.data.models.UserDetail
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    private val _userDetail = MutableLiveData<UserDetail>()
    val userDetail: LiveData<UserDetail> = _userDetail

    private val _listFollower = MutableLiveData<List<User>>()
    val listFollower: LiveData<List<User>> = _listFollower

    private val _listFollowing = MutableLiveData<List<User>>()
    val listFollowing: LiveData<List<User>> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    fun getUserDetail(username: String) {
        _isLoading.value = true
        _status.value = ""
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseUserDetail = response.body() as UserDetailResponse
                    val convertedUserDetail = UserDetail(
                        responseUserDetail.login,
                        responseUserDetail.avatarUrl,
                        responseUserDetail.name,
                        responseUserDetail.company,
                        responseUserDetail.location,
                        responseUserDetail.publicRepos,
                        responseUserDetail.followers,
                        responseUserDetail.following
                    )

                    _status.value = ""
                    _userDetail.value = convertedUserDetail
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _isLoading.value = false
                _userDetail.value = UserDetail()
                _status.value = "Terjadi kesalahan. Mohon coba beberapa saat lagi"
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowers(username: String) {
        _isLoading.value = true
        _status.value = ""
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<UserFollowResponse>> {
            override fun onResponse(
                call: Call<List<UserFollowResponse>>,
                response: Response<List<UserFollowResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseFollowers = response.body()
                    val convertedFollowers = ArrayList<User>()

                    responseFollowers?.forEach {
                        convertedFollowers.add(User(it.login, it.avatarUrl))
                    }

                    _status.value = ""
                    _listFollower.postValue(convertedFollowers)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserFollowResponse>>, t: Throwable) {
                _isLoading.value = false
                _listFollower.value = ArrayList()
                _status.value = "Terjadi kesalahan. Mohon coba beberapa saat lagi"
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowing(username: String) {
        _isLoading.value = true
        _status.value = ""
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<UserFollowResponse>> {
            override fun onResponse(
                call: Call<List<UserFollowResponse>>,
                response: Response<List<UserFollowResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseFollowing = response.body()
                    val convertedFollowing = ArrayList<User>()

                    responseFollowing?.forEach {
                        convertedFollowing.add(User(it.login, it.avatarUrl))
                    }

                    _status.value = ""
                    _listFollowing.postValue(convertedFollowing)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserFollowResponse>>, t: Throwable) {
                _isLoading.value = false
                _listFollowing.value = ArrayList()
                _status.value = "Terjadi kesalahan. Mohon coba beberapa saat lagi"
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun insertToFavorites(favorites: Favorites) {
        viewModelScope.launch {
            mFavoriteRepository.insert(favorites)
        }
    }

    fun removeFromFavorites(login: String) {
        viewModelScope.launch {
            mFavoriteRepository.delete(login)
        }
    }

    fun checkIfExist(login: String): LiveData<Favorites> =
        mFavoriteRepository.getFavoriteByLogin(login)

    companion object {
        private const val TAG = "UserDetailViewModel"
    }
}