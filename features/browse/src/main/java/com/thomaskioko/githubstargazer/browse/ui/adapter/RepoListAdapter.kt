package com.thomaskioko.githubstargazer.browse.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thomaskioko.githubstargazer.browse.data.model.RepoViewDataModel
import com.thomaskioko.githubstargazer.browse.databinding.ItemRepoBinding
import java.util.*

class RepoListAdapter constructor(
    private val repoItemClick: RepoItemClick
) : RecyclerView.Adapter<RepoItemViewHolder>() {

    private var itemsList: MutableList<RepoViewDataModel> = ArrayList()

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

    fun setData(repoViewModelDataList: ArrayList<RepoViewDataModel>) {
        itemsList.clear()
        itemsList = repoViewModelDataList
        notifyDataSetChanged()
    }

}

class RepoItemViewHolder(
    private val itemRepoBinding: ItemRepoBinding
) : RecyclerView.ViewHolder(itemRepoBinding.root) {

    fun bind(repoViewDataModel: RepoViewDataModel, itemClick: RepoItemClick) {
        itemRepoBinding.run {
            this.repo = repoViewDataModel
            clickHandler = itemClick
            executePendingBindings()
        }
    }
}

interface RepoItemClick {
    fun onClick(view: View, repoId: Int)
}