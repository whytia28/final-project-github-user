package com.example.consumerapp

import android.database.Cursor

object MappingHelper {
    fun cursorToArrayList(cursor: Cursor?): ArrayList<User> {
        val users = ArrayList<User>()
        cursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow("id"))
                val login = getString(getColumnIndexOrThrow("login"))
                val avatarUrl = getString(getColumnIndexOrThrow("avatar_url"))
                users.add(User(id, login, avatarUrl, null, null, null, null, null))
            }
        }
        return users
    }
}

