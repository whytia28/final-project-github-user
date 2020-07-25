package com.example.consumerapp

import android.content.Context
import android.net.Uri
import android.telecom.Call
import android.util.Log
import androidx.core.content.contentValuesOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalprojectgithubuser.activity.DetailActivity
import com.example.finalprojectgithubuser.activity.MainActivity
import com.example.finalprojectgithubuser.api.ApiService
import com.example.finalprojectgithubuser.helper.MappingHelper
import com.example.finalprojectgithubuser.model.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailViewModel : ViewModel() {

    private val detailUser = MutableLiveData<User>()
    private val favorite = MutableLiveData<User>()
    private val favorites = MutableLiveData<ArrayList<User>>()

    fun getDetailUser(): LiveData<User> = detailUser
    fun getFavorite(): LiveData<User> = favorite
    fun getFavorites(): LiveData<ArrayList<User>> = favorites

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

    fun insertFavorite(context: Context, user: User) {
        GlobalScope.launch {
            try {
                val values = contentValuesOf(
                    "id" to user.id,
                    "login" to user.login,
                    "avatar_url" to user.avatar
                )
                context.contentResolver.insert(DetailActivity.URI_FAVORITE, values)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setFavorite(context: Context) {
        GlobalScope.launch {
            try {
                val cursor = context.contentResolver.query(
                    DetailActivity.URI_FAVORITE,
                    null,
                    null,
                    null,
                    null,
                    null
                )
                favorites.postValue(MappingHelper.cursorToArrayList(cursor))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteFavorite(context: Context, user: User) {
        GlobalScope.launch {
            try {
                val uriWithId = Uri.parse("${DetailActivity.URI_FAVORITE}/${user.id}")
                context.contentResolver.delete(uriWithId, null, null)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}