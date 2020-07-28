package com.example.finalprojectgithubuser.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalprojectgithubuser.R
import com.example.finalprojectgithubuser.adapter.UserAdapter
import com.example.finalprojectgithubuser.model.User
import com.example.finalprojectgithubuser.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TOKEN = "token 1a6cfe6400a0305f3cfa98868c5235b6d8e5498a"
    }

    private lateinit var adapter: UserAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = getString(R.string.main_title)

        showRecyclerView()
        setupViewModel()

    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
        mainViewModel.getUser().observe(this@MainActivity, Observer { userItems ->
            if (userItems != null) {
                adapter.setData(userItems)
                showLoading(false)
            }
        })
    }

    private fun showRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        recycler_view.adapter = adapter
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val mainIntent = Intent(this@MainActivity, DetailActivity::class.java)
                mainIntent.putExtra(DetailActivity.EXTRA_USER, data)
                startActivity(mainIntent)
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.type_some_keyword)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(login: String): Boolean {
                showLoading(true)
                mainViewModel.setQueryUser(login)
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_setting -> {
                val intentSetting = Intent(this, SettingsActivity::class.java)
                startActivity(intentSetting)
            }

            R.id.action_favorite -> {
                val intentFavorite = Intent(this, FavoriteActivity::class.java)
                startActivity(intentFavorite)
            }

        }

        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(state: Boolean) {
        when (state) {
            true -> {
                progress_bar.visibility = View.VISIBLE
            }
            false -> {
                progress_bar.visibility = View.GONE
            }
        }
    }
}