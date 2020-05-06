package com.abzagabekov.tournamentapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abzagabekov.tournamentapp.databinding.ItemViewTournamentBinding
import com.abzagabekov.tournamentapp.pojo.Tournament

/**
 * Created by abzagabekov on 05.05.2020.
 * email: abzagabekov@gmail.com
 */

class TournamentsAdapter(private val onClickListener: OnClickListener) : ListAdapter<Tournament, TournamentsAdapter.TournamentViewHolder>(DiffCallback) {
    companion object DiffCallback : DiffUtil.ItemCallback<Tournament>() {
        override fun areItemsTheSame(oldItem: Tournament, newItem: Tournament): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Tournament, newItem: Tournament): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TournamentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemViewTournamentBinding.inflate(layoutInflater, parent, false)
        return TournamentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TournamentsAdapter.TournamentViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
        holder.bind(item)
    }

    class TournamentViewHolder(private val binding: ItemViewTournamentBinding) : RecyclerView.ViewHolder(binding.root) {
       fun bind(tournament: Tournament) {
           binding.tournament = tournament
           binding.executePendingBindings()
       }
    }

    class OnClickListener(val clickListener: (tournament: Tournament) -> Unit) {
        fun onClick(tournament: Tournament) = clickListener(tournament)
    }

}