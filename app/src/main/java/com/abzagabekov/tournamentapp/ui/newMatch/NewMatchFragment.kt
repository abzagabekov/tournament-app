package com.abzagabekov.tournamentapp.ui.newMatch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.abzagabekov.tournamentapp.App
import com.abzagabekov.tournamentapp.databinding.NewMatchFragmentBinding
import com.abzagabekov.tournamentapp.getTeams
import com.abzagabekov.tournamentapp.pojo.Team
import com.abzagabekov.tournamentapp.ui.ViewModelFactory
import com.abzagabekov.tournamentapp.ui.newTournament.NewTournamentFragmentDirections
import kotlinx.android.synthetic.main.new_match_fragment.*
import javax.inject.Inject

/**
 * Created by abzagabekov on 05.05.2020.
 * email: abzagabekov@gmail.com
 */

class NewMatchFragment : Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: NewMatchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = NewMatchFragmentBinding.inflate(inflater)

        App.appComponent.inject(this)

        viewModel = ViewModelProvider(this, viewModelFactory).get(NewMatchViewModel::class.java)

        val arguments = NewMatchFragmentArgs.fromBundle(requireArguments())
        viewModel.initViewModel(arguments.tournamentId, arguments.match)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.teams.observe(viewLifecycleOwner, Observer {
            it?.let {
                initSpinners(binding.spFirstTeam, binding.spSecondTeam, it)
                //initSpinner(binding.spSecondTeam, it)
            }
        })

        viewModel.eventCreateNewMatch.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })

        viewModel.navigateToTournamentMenu.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigateUp()
                viewModel.doneFinishMatch()
            }
        })



        return binding.root
    }

    private fun initSpinners(spinnerHome: Spinner, spinnerAway: Spinner, data: List<Team>) {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            data.map { it.name }
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerHome.adapter = adapter
        spinnerAway.adapter = adapter

        viewModel.currentMatch?.let {match ->
            val homeTeam = data.filter { it.id == match.homeTeam }[0]
            val awayTeam = data.filter { it.id == match.awayTeam }[0]

            val homeTeamPosition = adapter.getPosition(homeTeam.name)
            val awayTeamPosition = adapter.getPosition(awayTeam.name)
            spinnerHome.setSelection(homeTeamPosition)
            spinnerAway.setSelection(awayTeamPosition)
        }

    }


}
