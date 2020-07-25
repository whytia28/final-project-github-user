package com.example.finalprojectgithubuser.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalprojectgithubuser.R
import com.example.finalprojectgithubuser.model.User
import com.example.finalprojectgithubuser.adapter.FollowingFollowersAdapter
import com.example.finalprojectgithubuser.viewmodel.FollowersViewModel
import com.example.finalprojectgithubuser.viewmodel.FollowingViewModel
import kotlinx.android.synthetic.main.fragment_following_followers.*


class FollowingFollowersFragment : Fragment() {

    companion object {
        const val EXTRA_USER = "extra_user"
        const val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(index: Int): FollowingFollowersFragment {
            val fragment = FollowingFollowersFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_SECTION_NUMBER, index)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var username: User? = null
    private lateinit var adapter: FollowingFollowersAdapter
    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var followersViewModel: FollowersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var index = 1
        if (arguments != null) {
            arguments?.let { index = it.getInt(ARG_SECTION_NUMBER, 0) }

        }

        when (index) {
            1 -> {
                showRecyclerView()
                setFollowers()
            }
            else -> {
                showRecyclerView()
                setFollowing()
            }
        }
    }

    private fun setFollowing() {
        followingViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)


        username = activity!!.intent.getParcelableExtra(EXTRA_USER) as User

        username?.login?.let { followingViewModel.setFollowing(it) }

        followingViewModel.getFollowing().observe(activity!!, Observer { listFollowing ->
            if (listFollowing != null) {
                adapter.setData(listFollowing)
                progress_bar.visibility = View.GONE
            }
        })
    }

    private fun setFollowers() {
        followersViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowersViewModel::class.java)

        username = activity!!.intent.getParcelableExtra(EXTRA_USER) as User

        username?.login?.let { followersViewModel.setFollowers(it) }

        followersViewModel.getFollowers().observe(activity!!, Observer { listFollowers ->
            if (listFollowers != null) {
                adapter.setData(listFollowers)
                progress_bar.visibility = View.GONE
            }
        })
    }

    private fun showRecyclerView() {
        adapter = FollowingFollowersAdapter()
        rv_following.layoutManager = LinearLayoutManager(activity)
        rv_following.adapter = adapter
        rv_following.setHasFixedSize(true)
    }
}