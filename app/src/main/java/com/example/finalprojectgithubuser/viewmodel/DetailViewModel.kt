package com.example.finalprojectgithubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalprojectgithubuser.model.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class DetailViewModel : ViewModel() {
    val detailUser = MutableLiveData<User>()

    fun setDetailUser(user: String) {
        val url = "https://api.github.com/users/$user"

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
                    val userItems = User()
                    userItems.name = responseObject.getString("name")
                    userItems.location = responseObject.getString("location")
                    userItems.company = responseObject.getString("company")
                    detailUser.postValue(userItems)
                } catch (e: Exception) {
                    e.printStackTrace()
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

    fun getDetailUser(): LiveData<User> {
        return detailUser
    }
}