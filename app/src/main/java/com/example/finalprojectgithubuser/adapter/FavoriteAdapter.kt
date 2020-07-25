package com.example.finalprojectgithubuser.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalprojectgithubuser.R
import com.example.finalprojectgithubuser.activity.FavoriteActivity
import com.example.finalprojectgithubuser.model.User
import com.example.finalprojectgithubuser.model.UserDatabase
import kotlinx.android.synthetic.main.item_favorite.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteAdapter :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private var mUser = ArrayList<User>()

    fun setData(data: ArrayList<User>) {
        mUser.clear()
        mUser.addAll(data)
        notifyDataSetChanged()
    }

    private var onItemClickCallback: OnItemClickCallback? = null

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
                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(user) }
                iv_delete.setOnClickListener {
                    val builder =
                        AlertDialog.Builder(it.context).setPositiveButton(R.string.yes) { _, _ ->
                            val userData = UserDatabase.getInstance(it.context)
                            GlobalScope.launch {
                                val result = userData?.userDao()?.deleteUser(user)
                                (it.context as FavoriteActivity).runOnUiThread {
                                    if (result != 0) {
                                        Toast.makeText(
                                            it.context,
                                            context.getString(R.string.success_delete),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Toast.makeText(
                                            it.context,
                                            context.getString(R.string.failed_delete),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    (it.context as FavoriteActivity).setupData()
                                }
                            }
                        }.setNegativeButton(R.string.no) { p0, _ ->
                    p0.dismiss()
                }.setMessage(R.string.delete_confirm).setTitle(R.string.delete_title).show()
                builder.create()
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}