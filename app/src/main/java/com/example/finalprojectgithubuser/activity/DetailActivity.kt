package com.example.finalprojectgithubuser.activity

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.finalprojectgithubuser.R
import com.example.finalprojectgithubuser.adapter.PagerAdapter
import com.example.finalprojectgithubuser.model.User
import com.example.finalprojectgithubuser.model.UserDatabase
import com.example.finalprojectgithubuser.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private var user: User? = null
    private lateinit var detailViewModel: DetailViewModel
    private var isAdded: Boolean = false

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.title = getString(R.string.detail_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setViewPager()
        setDetail()
        setupViewModel()
        isFavoriteAdded()

        btn_favorite.setOnClickListener(this)
        btn_un_favorite.setOnClickListener(this)
    }

    override fun onResume() {
        setViewPager()
        setDetail()
        setupViewModel()
        isFavoriteAdded()
        super.onResume()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setDetail() {
        if (intent.hasExtra("login")) {
            intent.getStringExtra("login")?.let {
                user?.login = it
            }
            intent.getStringExtra("avatar_url")?.let {
                user?.avatar = it
            }
        }
        if (intent.hasExtra(EXTRA_USER)) {
            user = intent.getParcelableExtra(EXTRA_USER)
        }

        tv_username.text = user?.login
        user?.avatar.apply {
            Glide.with(this@DetailActivity).load(user?.avatar).into(avatar)
        }
    }

    private fun isFavoriteAdded() {

        val id = user?.id
        if (id != null) {
            detailViewModel.setFavorite(this, id)
        }
        detailViewModel.getFavorite().observe(this, Observer {
            if (it != null) {
                isAdded = true
                btn_un_favorite.visibility = View.VISIBLE
            } else {
                btn_un_favorite.visibility = View.GONE
            }
        })
    }

    private fun setupViewModel() {
        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

        user?.login?.let { detailViewModel.setDetailUser(it) }
        detailViewModel.getDetailUser().observe(this@DetailActivity, Observer { userItems ->
            if (userItems != null) {
                tv_name.text = userItems.name
                tv_company.text = userItems.company
                tv_location.text = userItems.location
                Log.d("detail", "getDetail success")
            }
        })
    }

    private fun setViewPager() {
        val pagerAdapter = PagerAdapter(
            this,
            supportFragmentManager
        )
        view_pager.adapter = pagerAdapter
        tabs.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_favorite -> {
                user.let {
                    if (it != null) {
                        detailViewModel.insertFavorite(this@DetailActivity, it)
                    }
                }
                Toast.makeText(this, this.getString(R.string.add_favorite), Toast.LENGTH_SHORT)
                    .show()
                isFavoriteAdded()
            }


            R.id.btn_un_favorite -> {
                GlobalScope.launch {
                    user.let {
                        if (it != null) {
                            UserDatabase.getInstance(this@DetailActivity)?.userDao()?.deleteUser(it)
                        }
                    }
                }
                Toast.makeText(this, this.getString(R.string.success_delete), Toast.LENGTH_SHORT)
                    .show()
                isFavoriteAdded()
            }
        }
    }
}