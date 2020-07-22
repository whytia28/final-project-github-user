package com.example.finalprojectgithubuser.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalprojectgithubuser.activity.MainActivity
import com.example.finalprojectgithubuser.api.ApiService
import com.example.finalprojectgithubuser.model.User
import com.example.finalprojectgithubuser.model.UserQuery
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel : ViewModel() {
    private val listUser = MutableLiveData<ArrayList<User>>()

    fun getUser(): LiveData<ArrayList<User>> = listUser

    fun setQueryUser(login: String) {
        ApiService.invoke().queryUser(MainActivity.TOKEN, login)
            .enqueue(object : Callback<UserQuery> {
                override fun onFailure(call: Call<UserQuery>, t: Throwable) {
                    Log.d("onFailure", t.toString())
                }

                override fun onResponse(call: Call<UserQuery>, response: Response<UserQuery>) {
                    listUser.postValue(response.body()?.items)
                }

            })
    }
}