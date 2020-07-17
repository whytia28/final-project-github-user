package com.example.githubuserapi.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuserapi.R
import com.example.githubuserapi.model.UserItems
import com.example.githubuserapi.adapter.PagerAdapter
import com.example.githubuserapi.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private var userItems: UserItems? = null
    private lateinit var detailViewModel: DetailViewModel

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.title = getString(R.string.detail_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setViewPager()
        setDetail()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setDetail() {
        userItems = intent.getParcelableExtra(EXTRA_USER) as UserItems
        tv_username.text = userItems!!.login
        userItems!!.avatar.apply {
            Glide.with(this@DetailActivity).load(userItems!!.avatar).into(avatar)
        }

        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

        userItems?.login?.let { detailViewModel.setDetailUser(it) }
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
}