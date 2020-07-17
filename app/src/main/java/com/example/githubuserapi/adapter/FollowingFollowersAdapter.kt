package com.example.githubuserapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapi.R
import com.example.githubuserapi.model.UserItems
import kotlinx.android.synthetic.main.user_items.view.*

class FollowingFollowersAdapter :
    RecyclerView.Adapter<FollowingFollowersAdapter.FollowingViewHolder>() {

    private val listData = ArrayList<UserItems>()

    fun setData(items: ArrayList<UserItems>) {
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
        fun bind(userItems: UserItems) {
            with(itemView) {
                userItems.avatar.apply {
                    Glide.with(itemView).load(userItems.avatar).into(avatar)
                }
                tv_username.text = userItems.login
            }
        }
    }
}