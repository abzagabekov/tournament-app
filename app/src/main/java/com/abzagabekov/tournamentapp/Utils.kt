package com.abzagabekov.tournamentapp

import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.widget.RecyclerView
import com.abzagabekov.tournamentapp.pojo.Match
import com.abzagabekov.tournamentapp.pojo.Team
import com.abzagabekov.tournamentapp.pojo.Tournament
import com.abzagabekov.tournamentapp.ui.home.TournamentsAdapter

/**
 * Created by abzagabekov on 05.05.2020.
 * email: abzagabekov@gmail.com
 */

const val TYPE_LEAGUE = 0
const val TYPE_KNOCKOUT = 1

class TournamentKeyProvider(private val adapter: TournamentsAdapter) :
    ItemKeyProvider<Tournament>(ItemKeyProvider.SCOPE_CACHED) {

    override fun getKey(position: Int): Tournament {
        return adapter.currentList[position]
    }
    override fun getPosition(key: Tournament): Int {
        return adapter.currentList.indexOf(key)
    }
}

class TournamentLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<Tournament>() {
    override fun getItemDetails(e: MotionEvent): ItemDetails<Tournament>? {
        return recyclerView.findChildViewUnder(e.x, e.y)?.let {
            (recyclerView.getChildViewHolder(it) as? TournamentsAdapter.ViewHolderWithDetails<Tournament>)?.getItemDetail()
        }
    }
}

interface AssistedSavedStateViewModelFactory<T : ViewModel> {
    fun create(savedStateHandle: SavedStateHandle): T
}