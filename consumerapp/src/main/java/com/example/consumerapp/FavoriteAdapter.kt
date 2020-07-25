package com.example.consumerapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_favorite.view.*

class FavoriteAdapter(private var mUser: ArrayList<User>) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    fun setData(items: ArrayList<User>) {
        mUser.clear()
        mUser.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mUser[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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