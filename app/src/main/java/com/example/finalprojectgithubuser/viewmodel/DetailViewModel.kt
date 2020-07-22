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


class DetailViewModel : ViewModel() {
    private val detailUser = MutableLiveData<User>()

    fun getDetailUser(): LiveData<User> = detailUser

    fun setDetailUser(login: String) {
        ApiService.invoke().getUserDetails(MainActivity.TOKEN, login)
            .enqueue(object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.d("onFailure", t.toString())
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    detailUser.postValue(response.body())
                }

            })
    }
}