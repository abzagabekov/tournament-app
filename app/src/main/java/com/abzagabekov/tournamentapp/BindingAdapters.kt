package com.abzagabekov.tournamentapp

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abzagabekov.tournamentapp.pojo.Tournament
import com.abzagabekov.tournamentapp.ui.home.TournamentsAdapter

/**
 * Created by abzagabekov on 05.05.2020.
 * email: abzagabekov@gmail.com
 */

@BindingAdapter("listDataTournament")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Tournament>?) {
    val adapter = recyclerView.adapter as TournamentsAdapter
    adapter.submitList(data)
}