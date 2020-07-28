package com.example.finalprojectgithubuser.viewmodel

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.util.Log
import androidx.core.content.contentValuesOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalprojectgithubuser.R
import com.example.finalprojectgithubuser.activity.DetailActivity
import com.example.finalprojectgithubuser.activity.MainActivity
import com.example.finalprojectgithubuser.api.ApiService
import com.example.finalprojectgithubuser.helper.MappingHelper
import com.example.finalprojectgithubuser.model.User
import com.example.finalprojectgithubuser.model.UserDatabase
import com.example.finalprojectgithubuser.widget.FavoriteUserWidget
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailViewModel : ViewModel() {

    private val detailUser = MutableLiveData<User>()
    private val favorite = MutableLiveData<User>()
    private val favorites = MutableLiveData<List<User>>()

    fun getDetailUser(): LiveData<User> = detailUser
    fun getFavorite(): LiveData<User> = favorite
    fun getFavorites(): LiveData<List<User>> = favorites

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
                widgetUpdate(context)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setFavorite(context: Context, id: Int) {
        GlobalScope.launch {
            try {
                favorite.postValue(UserDatabase.getInstance(context)?.userDao()?.getUserById(id))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setFavorites(context: Context) {
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


    private fun widgetUpdate(context: Context) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val widget = ComponentName(context, FavoriteUserWidget::class.java)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(widget)
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.sv_widget_favorite)
    }
}