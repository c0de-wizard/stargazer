package com.thomaskioko.stargazer.browse.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thomaskioko.githubstargazer.browse.databinding.ItemRepoBinding
import com.thomaskioko.stargazer.browse.model.RepoViewDataModel
import com.thomaskioko.stargazers.ui.extensions.setAllCornerSizes
import kotlin.properties.Delegates

internal class RepoListAdapter constructor(
    private val repoItemClick: RepoItemClick
) : RecyclerView.Adapter<RepoItemViewHolder>() {

    var itemsList: List<RepoViewDataModel> by Delegates.observable(emptyList()) { _, new, old ->
        if (new != old) notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoItemViewHolder {
        return RepoItemViewHolder(
            ItemRepoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RepoItemViewHolder, position: Int) {
        holder.bind(requireNotNull(getItem(position)), repoItemClick)
    }

    override fun getItemCount(): Int = itemsList.size

    private fun getItem(position: Int): RepoViewDataModel? = itemsList[position]
}

internal class RepoItemViewHolder(
    private val itemRepoBinding: ItemRepoBinding
) : RecyclerView.ViewHolder(itemRepoBinding.root) {

    fun bind(repoViewDataModel: RepoViewDataModel, itemClick: RepoItemClick) {
        itemRepoBinding.run {
            this.repo = repoViewDataModel
            clickHandler = itemClick
            repoItemCardview.setAllCornerSizes(0F)
            executePendingBindings()
        }
    }
}

interface RepoItemClick {
    fun onClick(view: View, repoId: Long)
}
