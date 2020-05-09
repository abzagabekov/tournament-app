package com.abzagabekov.tournamentapp.ui.tournament

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.abzagabekov.tournamentapp.R
import com.abzagabekov.tournamentapp.databinding.TournamentFragmentBinding
import kotlinx.android.synthetic.main.app_bar_main.*

/**
 * Created by abzagabekov on 05.05.2020.
 * email: abzagabekov@gmail.com
 */

class TournamentFragment : Fragment() {

    lateinit var viewModel: TournamentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = TournamentFragmentBinding.inflate(inflater)

        viewModel = ViewModelProvider(this).get(TournamentViewModel::class.java)

        val arguments = TournamentFragmentArgs.fromBundle(requireArguments())
        viewModel.currentTournament = arguments.tournament

        requireActivity().toolbar.title = viewModel.currentTournament.name

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.navigateToFixtures.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(TournamentFragmentDirections.actionTournamentFragmentToFixturesFragment(it))
                viewModel.showFixturesComplete()
            }
        })

        viewModel.navigateToTables.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigate(TournamentFragmentDirections.actionTournamentFragmentToTablesFragment())
                viewModel.showTablesComplete()
            }
        })

        viewModel.navigateToNewMatch.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigate(TournamentFragmentDirections.actionTournamentFragmentToNewMatchFragment(null, viewModel.currentTournament.id))
                viewModel.playNewMatchComplete()
            }
        })

        viewModel.navigateToTeams.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(TournamentFragmentDirections.actionTournamentFragmentToTeamsFragment(it))
                viewModel.showTeamsComplete()
            }
        })

        return binding.root
    }

}
