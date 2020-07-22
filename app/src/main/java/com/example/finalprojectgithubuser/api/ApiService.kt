package com.example.finalprojectgithubuser.api

import com.example.finalprojectgithubuser.model.User
import com.example.finalprojectgithubuser.model.UserQuery
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/search/users")
    fun queryUser(
        @Header("Authorization") auth: String,
        @Query("q") login: String?
    ): Call<UserQuery>

    @GET("/users/{login}")
    fun getUserDetails(
        @Header("Authorization") auth: String,
        @Path("login") login: String?
    ): Call<User>

    @GET("users/{login}/followers")
    fun getFollowers(
        @Header("Authorization") auth: String,
        @Path("login") login: String?
    ): Call<ArrayList<User>>

    @GET("users/{login}/following")
    fun getFollowing(
        @Header("Authorization") auth: String,
        @Path("login") login: String?
    ): Call<ArrayList<User>>

    companion object {
        operator fun invoke(): ApiService {
            return Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}