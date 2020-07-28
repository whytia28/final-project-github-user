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
    @ColumnInfo(name = "login") var login: String?,

    @SerializedName("avatar_url")
    @ColumnInfo(name = "avatar_url") var avatar: String?,

    @SerializedName("name")
    var name: String?,

    @SerializedName("company")
    var company: String?,

    @SerializedName("location")
    var location: String?,

    @SerializedName("followers")
    var followers: Int? = 0,

    @SerializedName("following")
    var following: Int? = 0
) : Parcelable