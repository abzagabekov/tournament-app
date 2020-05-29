package com.abzagabekov.tournamentapp.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abzagabekov.tournamentapp.R
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

@BindingAdapter(value = ["matchBindHome", "teams"])
fun bindMatchTeamHome(textView: TextView, matchBindHome: Match, teams: List<Team>?) {
    teams?.let {
        val homeTeam = teams.filter { it.id == matchBindHome.homeTeam }
        textView.text = homeTeam[0].name
    }
}

@BindingAdapter(value = ["matchBindAway", "teams"])
fun bindMatchTeamAway(textView: TextView, matchBindAway: Match, teams: List<Team>?) {
    teams?.let {
        val awayTeam = teams.filter {it.id == matchBindAway.awayTeam}
        textView.text = awayTeam[0].name
    }
}

@BindingAdapter("teamGoals")
fun bindMatchTeamAway(textView: TextView, match: Match) {
    textView.text = when(textView.id) {
        R.id.tv_goals_team_1 -> if (match.homeTeamGoals != null) match.homeTeamGoals.toString() else ""
        else -> if (match.awayTeamGoals != null) match.awayTeamGoals.toString() else ""
    }
}