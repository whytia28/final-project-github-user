package com.example.finalprojectgithubuser.widget

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.example.finalprojectgithubuser.R
import com.example.finalprojectgithubuser.activity.DetailActivity
import com.example.finalprojectgithubuser.helper.MappingHelper
import com.example.finalprojectgithubuser.model.User

internal class StackRemoteViewsFactory(private val context: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private var widgetItem = ArrayList<Bitmap>()
    private var userItems = ArrayList<User>()
    private var cursor: Cursor? = null


    override fun onCreate() {

    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = 0

    override fun onDataSetChanged() {
        cursor?.close()
        userItems.clear()
        widgetItem.clear()

        val token = Binder.clearCallingIdentity()

        cursor =
            context.contentResolver.query(DetailActivity.URI_FAVORITE, null, null, null, null, null)
        userItems = MappingHelper.cursorToArrayList(cursor)
        userItems.forEachIndexed { index, _ ->
            val bitmap = Glide.with(context)
                .asBitmap()
                .load(userItems[index].avatar)
                .submit()
                .get()
            widgetItem.add(bitmap)

        }

        Binder.restoreCallingIdentity(token)
    }

    override fun hasStableIds(): Boolean = false

    override fun getViewAt(position: Int): RemoteViews {
        val remote = RemoteViews(context.packageName, R.layout.item_favorite_widget)

        if (widgetItem.size >= 1) {
            val intent = Intent(context, DetailActivity::class.java)
                .putExtra("extra_user" , userItems[position])
            remote.setImageViewBitmap(R.id.iv_widget_favorite, widgetItem[position])
            remote.setOnClickFillInIntent(R.id.iv_widget_favorite, intent)
        }
        return remote
    }

    override fun getCount(): Int = widgetItem.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {

    }
}