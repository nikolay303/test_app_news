package com.android.test_app_news.ui.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.test_app_news.R
import com.android.test_app_news.data.db.model.Post
import com.android.test_app_news.databinding.ItemPostBinding


/**
 * Created by Nikolay Siliuk on 15.08.21.
 */

class PostsAdapter(private val onPostClick: (Post) -> Unit) :
    ListAdapter<Post, PostsAdapter.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false),
            onPostClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        itemView: View,
        private val onPostClick: (Post) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemPostBinding.bind(itemView)

        fun bind(post: Post) {
            binding.tvTitle.text = post.title
            binding.root.setOnClickListener { onPostClick.invoke(post) }
        }
    }
}