package com.example.finalprojectgithubuser.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "user")
@Parcelize
data class User(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true) var id: Int = 0,

    @SerializedName("login")
    @ColumnInfo(name = "login") var login: String? = null,

    @SerializedName("avatar_url")
    @ColumnInfo(name = "avatar_url") var avatar: String? = null,

    @SerializedName("name")
    @ColumnInfo(name = "name") var name: String? = null,

    @SerializedName("company")
    var company: String? = null,

    @SerializedName("location")
    var location: String? = null,

    @SerializedName("followers")
    var followers: Int = 0,

    @SerializedName("following")
    var following: Int = 0
) : Parcelable