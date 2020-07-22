package com.example.finalprojectgithubuser.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.finalprojectgithubuser.R
import com.example.finalprojectgithubuser.model.User
import com.example.finalprojectgithubuser.adapter.PagerAdapter
import com.example.finalprojectgithubuser.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private var user: User? = null
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
        user = intent.getParcelableExtra(EXTRA_USER) as User
        tv_username.text = user!!.login
        user!!.avatar.apply {
            Glide.with(this@DetailActivity).load(user!!.avatar).into(avatar)
        }

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
}