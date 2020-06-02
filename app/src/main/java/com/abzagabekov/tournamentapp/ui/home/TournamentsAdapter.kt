package com.abzagabekov.tournamentapp.ui.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.service.voice.AlwaysOnHotwordDetector
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abzagabekov.tournamentapp.R
import com.abzagabekov.tournamentapp.databinding.ItemViewTournamentBinding
import com.abzagabekov.tournamentapp.pojo.Tournament
import java.text.ParsePosition

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

    lateinit var tracker: SelectionTracker<Tournament>

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TournamentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemViewTournamentBinding.inflate(layoutInflater, parent, false)
        return TournamentViewHolder(binding)
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: TournamentsAdapter.TournamentViewHolder, position: Int, payloads: List<Any>) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }

        holder.setActivatedState(tracker.isSelected(item))

        if (SelectionTracker.SELECTION_CHANGED_MARKER !in payloads) {
            holder.bind(item)
        }

        if (tracker.isSelected(getItem(position))) {
            (holder.itemView as CardView).setCardBackgroundColor(Color.parseColor(
                holder.itemView.resources.getString(R.color.color_on_surface)))
        } else {
            (holder.itemView as CardView).setCardBackgroundColor(Color.parseColor(
                holder.itemView.resources.getString(R.color.gray_mercury)))
        }
    }

    inner class TournamentViewHolder(private val binding: ItemViewTournamentBinding) : RecyclerView.ViewHolder(binding.root), ViewHolderWithDetails<Tournament> {
       fun bind(tournament: Tournament) {
           binding.tournament = tournament
           binding.executePendingBindings()
       }

        override fun getItemDetail(): ItemDetailsLookup.ItemDetails<Tournament> {
            return TournamentDetails(bindingAdapterPosition, getItem(bindingAdapterPosition))
        }

       fun setActivatedState(isActivated: Boolean) {
           itemView.isActivated = isActivated
       }
    }

    interface ViewHolderWithDetails<TItem> {
        fun getItemDetail(): ItemDetailsLookup.ItemDetails<TItem>
    }

    class TournamentDetails(private val adapterPosition: Int, private val selectedKey: Tournament?) :
        ItemDetailsLookup.ItemDetails<Tournament>() {
        override fun getSelectionKey() = selectedKey
        override fun getPosition() = adapterPosition
    }

    class OnClickListener(val clickListener: (tournament: Tournament) -> Unit) {
        fun onClick(tournament: Tournament) = clickListener(tournament)
    }

    override fun onBindViewHolder(holder: TournamentViewHolder, position: Int) = Unit

}