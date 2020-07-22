package com.example.finalprojectgithubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalprojectgithubuser.activity.MainActivity
import com.example.finalprojectgithubuser.api.ApiService
import com.example.finalprojectgithubuser.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowersViewModel : ViewModel() {
    private val listFollowers = MutableLiveData<ArrayList<User>>()

    fun getFollowers(): LiveData<ArrayList<User>> = listFollowers

    fun setFollowers(login: String) {
        ApiService.invoke().getFollowers(MainActivity.TOKEN, login)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.d("onFailure", t.toString())
                }

                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    listFollowers.postValue(response.body())
                }

            })
    }
}