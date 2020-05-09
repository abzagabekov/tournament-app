package com.abzagabekov.tournamentapp.ui.fixtures

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abzagabekov.tournamentapp.databinding.ItemViewFixtureBinding
import com.abzagabekov.tournamentapp.pojo.Match

/**
 * Created by abzagabekov on 05.05.2020.
 * email: abzagabekov@gmail.com
 */
class FixturesAdapter(val viewModel: FixturesViewModel, private val clickListener: OnClickListener) : ListAdapter<Match, FixturesAdapter.FixturesViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Match>() {
        override fun areItemsTheSame(oldItem: Match, newItem: Match): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Match, newItem: Match): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FixturesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemViewFixtureBinding.inflate(inflater, parent, false)
        return FixturesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FixturesViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            clickListener.onClick(item)
        }
        holder.bind(item)
    }

    inner class FixturesViewHolder(private val binding: ItemViewFixtureBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(match: Match) {
            binding.match = match
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (match: Match) -> Unit) {
        fun onClick(match: Match) = clickListener(match)
    }

}