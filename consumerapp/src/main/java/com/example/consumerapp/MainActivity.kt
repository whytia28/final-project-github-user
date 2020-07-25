package com.example.consumerapp

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalprojectgithubuser.R
import com.example.finalprojectgithubuser.adapter.FavoriteAdapter
import com.example.finalprojectgithubuser.model.User
import com.example.finalprojectgithubuser.model.UserDatabase
import com.example.finalprojectgithubuser.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"

        private const val SCHEME = "content"
        private const val AUTHORITY = "com.example.finalprojectgithubuser"
        private const val TABLE_NAME = "user"
        val URI_FAVORITE: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()
    }

//    private var mDb: UserDatabase? = null
    private lateinit var adapter: FavoriteAdapter
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = getString(R.string.favorite)

//        mDb = UserDatabase.getInstance(this)

        rv_favorite.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_favorite.setHasFixedSize(true)

//        fetchData()
        setupUi()
    }

//    fun fetchData() {
//
//        GlobalScope.launch {
//            val listFavorite = mDb?.userDao()?.getAllUser()
//            runOnUiThread {
//                listFavorite?.let {
//                    adapter = FavoriteAdapter(it as ArrayList<User>)
//                    rv_favorite.adapter = adapter
//                }
//            }
//        }
//    }

    private fun setupUi() {
        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

        detailViewModel.getFavorites().observe(this, Observer {
            if (it.isEmpty()) {
                adapter.setData(it)
                tv_favorite_empty.visibility = View.VISIBLE
            } else {
                tv_favorite_empty.visibility = View.GONE
            }
        })
    }
}