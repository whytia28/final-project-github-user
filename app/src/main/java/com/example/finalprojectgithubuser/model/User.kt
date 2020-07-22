package com.example.finalprojectgithubuser.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "user")
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "login") var login: String? = null,
    @ColumnInfo(name = "avatar_url") var avatar: String? = null,
    @ColumnInfo(name = "name") var name: String? = null,
    var company: String? = null,
    var location: String? = null,
    var followers: Int = 0,
    var following: Int = 0
) : Parcelable