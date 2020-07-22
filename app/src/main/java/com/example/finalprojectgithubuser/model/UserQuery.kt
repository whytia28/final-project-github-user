package com.example.finalprojectgithubuser.model

import com.google.gson.annotations.SerializedName

data class UserQuery (
    @SerializedName("items")
    var items: ArrayList<User>
)