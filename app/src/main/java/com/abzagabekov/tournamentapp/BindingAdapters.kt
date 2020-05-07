package com.abzagabekov.tournamentapp

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abzagabekov.tournamentapp.pojo.Match
import com.abzagabekov.tournamentapp.pojo.Team
import com.abzagabekov.tournamentapp.pojo.Tournament
import com.abzagabekov.tournamentapp.ui.fixtures.FixturesAdapter
import com.abzagabekov.tournamentapp.ui.home.TournamentsAdapter
import com.abzagabekov.tournamentapp.ui.teams.TeamsAdapter

/**
 * Created by abzagabekov on 05.05.2020.
 * email: abzagabekov@gmail.com
 */

@BindingAdapter("listDataTournament")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Tournament>?) {
    val adapter = recyclerView.adapter as TournamentsAdapter
    adapter.submitList(data)
}

@BindingAdapter("listDataFixtures")
fun bindRVFixtures(recyclerView: RecyclerView, data: List<Match>?) {
    val adapter = recyclerView.adapter as FixturesAdapter
    adapter.submitList(data)
}

@BindingAdapter("listDataTeams")
fun bindRVTeams(recyclerView: RecyclerView, data: List<Team>?) {
    val adapter = recyclerView.adapter as TeamsAdapter
    adapter.submitList(data)
}