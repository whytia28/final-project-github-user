package com.example.finalprojectgithubuser.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.example.finalprojectgithubuser.model.User
import com.example.finalprojectgithubuser.model.UserDatabase

class FavoriteContentProvider : ContentProvider() {

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when (values) {
            null -> return null
            else -> context?.let {
                UserDatabase.getInstance(it)?.userDao()?.insert(fromContentValues(values))
            }
        }
        context?.contentResolver?.notifyChange(uri, null)
        return uri
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return context?.let { UserDatabase.getInstance(it)?.userDao()?.getUserCursor() }
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        context?.let {
            UserDatabase.getInstance(it)?.userDao()?.deleteUserById(ContentUris.parseId(uri))
        }
        context?.contentResolver?.notifyChange(uri, null)
        return 0
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    private fun fromContentValues(values: ContentValues): User {
        val id = values.getAsInteger("id")
        val login = values.getAsString("login")
        val avatarUrl = values.getAsString("avatar_url")
        return User(id, login, avatarUrl, null, null, null, null, null)
    }
}
