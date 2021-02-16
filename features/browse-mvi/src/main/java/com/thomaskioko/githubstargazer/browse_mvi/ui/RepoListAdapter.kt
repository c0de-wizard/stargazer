package com.thomaskioko.githubstargazer.browse_mvi.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thomaskioko.githubstargazer.browse_mvi.databinding.ItemRepositoryBinding
import com.thomaskioko.githubstargazer.browse_mvi.model.RepoViewDataModel
import com.thomaskioko.githubstargazers.ui.extensions.setAllCornerSizes
import java.util.*

internal class RepoListAdapter constructor(
    private val repoItemClick: RepoItemClick
) : RecyclerView.Adapter<RepoItemViewHolder>() {

    private var itemsList: List<RepoViewDataModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoItemViewHolder {
        return RepoItemViewHolder(
            ItemRepositoryBinding.inflate(
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

    fun setData(list: List<RepoViewDataModel>) {
        itemsList = list
        notifyDataSetChanged()
    }
}

internal class RepoItemViewHolder(
    private val itemRepositoryBinding: ItemRepositoryBinding
) : RecyclerView.ViewHolder(itemRepositoryBinding.root) {

    fun bind(repoViewDataModel: RepoViewDataModel, itemClick: RepoItemClick) {
        itemRepositoryBinding.run {
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
