package com.example.consumerapp

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {

        private const val SCHEME = "content"
        private const val AUTHORITY = "com.example.finalprojectgithubuser"
        private const val TABLE_NAME = "user"
        val URI_FAVORITE: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()
    }

    private lateinit var adapter: FavoriteAdapter
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = getString(R.string.favorite)

        setupUi()
        setRecyclerView()
    }

    private fun setupUi() {
        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)
        detailViewModel.setData(this)

        detailViewModel.getData().observe(this, Observer {
            adapter.setData(it)
            if (it.isEmpty()) {
                tv_favorite_empty.visibility = View.VISIBLE
            } else {
                tv_favorite_empty.visibility = View.GONE
            }
        })
    }

    private fun setRecyclerView() {
        rv_favorite.setHasFixedSize(true)
        rv_favorite.layoutManager = LinearLayoutManager(this)
        adapter = FavoriteAdapter()
        rv_favorite.adapter = adapter
    }
}