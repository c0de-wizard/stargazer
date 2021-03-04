package com.thomaskioko.stargazer.bookmarks.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thomaskioko.githubstargazer.bookmarks.databinding.ItemBookmarkedRepoBinding
import com.thomaskioko.stargazer.bookmarks.model.RepoViewDataModel
import com.thomaskioko.stargazers.ui.extensions.setAllCornerSizes
import kotlin.properties.Delegates

class BookmarkedReposAdapter constructor(
    private val bookmarkRepoItemClick: BookmarkRepoItemClick
) : RecyclerView.Adapter<RepoItemViewHolder>() {

    var itemsList: List<RepoViewDataModel> by Delegates.observable(emptyList()) { _, new, old ->
        if (new != old) notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoItemViewHolder {
        return RepoItemViewHolder(
            ItemBookmarkedRepoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RepoItemViewHolder, position: Int) {
        holder.bind(requireNotNull(getItem(position)), bookmarkRepoItemClick)
    }

    override fun getItemCount(): Int = itemsList.size

    private fun getItem(position: Int): RepoViewDataModel? = itemsList[position]

}

class RepoItemViewHolder(
    private val itemRepoBinding: ItemBookmarkedRepoBinding
) : RecyclerView.ViewHolder(itemRepoBinding.root) {

    fun bind(repoViewDataModel: RepoViewDataModel, itemClickBookmark: BookmarkRepoItemClick) {
        itemRepoBinding.run {
            this.repo = repoViewDataModel
            clickHandler = itemClickBookmark
            repoItemFavorite.setAllCornerSizes(0F)
            executePendingBindings()
        }
    }
}

interface BookmarkRepoItemClick {
    fun onClick(view: View, repoId: Long)
}
