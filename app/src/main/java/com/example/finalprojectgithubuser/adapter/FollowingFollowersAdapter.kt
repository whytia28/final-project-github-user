package com.example.finalprojectgithubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalprojectgithubuser.R
import com.example.finalprojectgithubuser.model.User
import kotlinx.android.synthetic.main.user_items.view.*

class FollowingFollowersAdapter :
    RecyclerView.Adapter<FollowingFollowersAdapter.FollowingViewHolder>() {

    private val listData = ArrayList<User>()

    fun setData(items: ArrayList<User>) {
        this.listData.clear()
        this.listData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FollowingViewHolder {
        val mView =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.user_items, viewGroup, false)
        return FollowingViewHolder(mView)
    }

    override fun getItemCount(): Int = this.listData.size

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        holder.bind(this.listData[position])
    }

    inner class FollowingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            with(itemView) {
                user.avatar.apply {
                    Glide.with(itemView).load(user.avatar).into(avatar)
                }
                tv_username.text = user.login
            }
        }
    }
}