package com.example.finalprojectgithubuser.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalprojectgithubuser.R
import com.example.finalprojectgithubuser.adapter.FavoriteAdapter
import com.example.finalprojectgithubuser.model.User
import com.example.finalprojectgithubuser.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    private lateinit var adapter: FavoriteAdapter
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        supportActionBar?.title = getString(R.string.favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupData()
        showRecyclerView()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        setupData()
        showRecyclerView()
        super.onResume()
    }

    fun setupData() {
        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)
        detailViewModel.setFavorites(this)

        detailViewModel.getFavorites().observe(this@FavoriteActivity, Observer {
            adapter.setData(it as ArrayList<User>)
            if (it.isNotEmpty()) {
                tv_favorite_empty.visibility = View.GONE
            } else {
                tv_favorite_empty.visibility = View.VISIBLE
            }
        })
    }

    private fun showRecyclerView() {
        rv_favorite.setHasFixedSize(true)
        rv_favorite.layoutManager = LinearLayoutManager(this)
        adapter = FavoriteAdapter()
        rv_favorite.adapter = adapter
        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val favIntent =
                    Intent(this@FavoriteActivity, DetailActivity::class.java)
                favIntent.putExtra(DetailActivity.EXTRA_USER, data)
                startActivity(favIntent)
            }
        })
    }
}