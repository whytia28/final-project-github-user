package com.example.finalprojectgithubuser.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import androidx.core.net.toUri
import com.example.finalprojectgithubuser.R
import com.example.finalprojectgithubuser.activity.DetailActivity

/**
 * Implementation of App Widget functionality.
 */
class FavoriteUserWidget : AppWidgetProvider() {

    companion object {

        const val EXTRA_USER = "com.example.EXTRA_USER"
        private const val TOAST_ACTION = "com.example.TOAST_ACTION"

        internal fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val intent = Intent(context, StackWidgetService::class.java).apply {
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                data = toUri(Intent.URI_INTENT_SCHEME).toUri()
            }

            val detailIntent = Intent(context, FavoriteUserWidget::class.java)
            detailIntent.action = TOAST_ACTION
            detailIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()
            val pendingIntent = PendingIntent.getBroadcast(context, 0, detailIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val views = RemoteViews(context.packageName, R.layout.favorite_user_widget).apply {
                setRemoteAdapter(R.id.sv_widget_favorite, intent)
                setEmptyView(R.id.sv_widget_favorite, R.id.tv_widget_empty)
                setPendingIntentTemplate(R.id.sv_widget_favorite, pendingIntent)
            }

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

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action != null) {
            if (intent.action == TOAST_ACTION) {
                val intentDetail = Intent(context, DetailActivity::class.java)
                intentDetail.putExtra("extra_user", intent.getStringExtra(EXTRA_USER))
                intentDetail.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intentDetail.data = Uri.parse(intentDetail.toUri(Intent.URI_INTENT_SCHEME))
                context.startActivity(intentDetail)
            }
        }
    }

}
