package com.example.githubuserapi.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapi.model.UserItems
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import java.lang.Exception

class FollowersViewModel : ViewModel() {
    val listFollowers = MutableLiveData<ArrayList<UserItems>>()

    fun setFollowers(user: String) {
        val listUsers = ArrayList<UserItems>()
        val url = "https://api.github.com/users/$user/followers"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "1a6cfe6400a0305f3cfa98868c5235b6d8e5498a")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONArray(result)

                    for (i in 0 until responseObject.length()) {
                        val users = responseObject.getJSONObject(i)
                        val userItems =
                            UserItems()
                        userItems.login = users.getString("login")
                        userItems.avatar = users.getString("avatar_url")
                        listUsers.add(userItems)
                    }
                    listFollowers.postValue(listUsers)
                } catch (e: Exception) {
                    Log.d("exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("onFailure", error?.message.toString())
            }
        })
    }

    fun getFollowers(): LiveData<ArrayList<UserItems>> {
        return listFollowers
    }
}