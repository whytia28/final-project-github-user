package com.example.finalprojectgithubuser.widget

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.net.toUri
import com.example.finalprojectgithubuser.R
import com.example.finalprojectgithubuser.activity.DetailActivity

/**
 * Implementation of App Widget functionality.
 */
class FavoriteUserWidget : AppWidgetProvider() {

    companion object {
        internal fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val intent = Intent(context, StackWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

            val detailIntent = Intent(context, DetailActivity::class.java)
                .putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)

            val pendingIntent = TaskStackBuilder.create(context)
                .addParentStack(DetailActivity::class.java)
                .addNextIntent(detailIntent)
                .getPendingIntent(110, PendingIntent.FLAG_UPDATE_CURRENT)

            val views = RemoteViews(context.packageName, R.layout.favorite_user_widget)
            views.setRemoteAdapter(R.id.sv_widget_favorite, intent)
            views.setEmptyView(R.id.sv_widget_favorite, R.id.tv_widget_empty)
            views.setPendingIntentTemplate(R.id.sv_widget_favorite, pendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

}
