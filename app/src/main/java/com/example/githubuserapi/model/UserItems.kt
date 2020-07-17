package com.example.githubuserapi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserItems(
    var id: Int = 0,
    var login: String? = null,
    var avatar: String? = null,
    var name: String? = null,
    var company: String? = null,
    var location: String? = null,
    var followers: Int = 0,
    var following: Int = 0
) : Parcelable