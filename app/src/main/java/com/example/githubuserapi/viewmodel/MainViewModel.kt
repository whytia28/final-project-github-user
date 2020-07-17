package com.example.githubuserapi.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapi.model.UserItems
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class MainViewModel : ViewModel() {
    val listUser = MutableLiveData<ArrayList<UserItems>>()

    fun setUser(users: String) {
        val listUsers = ArrayList<UserItems>()

        val url = "https://api.github.com/search/users?q=$users"

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
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("items")

                    for (i in 0 until list.length()) {
                        val user = list.getJSONObject(i)
                        val userItems =
                            UserItems()
                        userItems.id = user.getInt("id")
                        userItems.login = user.getString("login")
                        userItems.avatar = user.getString("avatar_url")
                        listUsers.add(userItems)
                    }

                    listUser.postValue(listUsers)
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
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Log.d("onFailure", "$errorMessage Failure")
            }
        })
    }

    fun getUser(): LiveData<ArrayList<UserItems>> {
        return listUser
    }
}