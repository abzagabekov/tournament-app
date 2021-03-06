package com.abzagabekov.tournamentapp.ui.fixtures

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.abzagabekov.tournamentapp.App
import com.abzagabekov.tournamentapp.R

import com.abzagabekov.tournamentapp.databinding.FixturesFragmentBinding
import com.abzagabekov.tournamentapp.ui.ViewModelFactory
import javax.inject.Inject

/**
 * Created by abzagabekov on 05.05.2020.
 * email: abzagabekov@gmail.com
 */

class FixturesFragment : Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: FixturesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FixturesFragmentBinding.inflate(inflater)

        App.appComponent.inject(this)

        viewModel = ViewModelProvider(this, viewModelFactory).get(FixturesViewModel::class.java)

        val arguments = FixturesFragmentArgs.fromBundle(requireArguments())
        viewModel.initViewModel(arguments.tournamentId, resources)


        binding.viewModel = viewModel

        viewModel.teams.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.lifecycleOwner = this
            }
        })

        val adapter = FixturesAdapter(viewModel, FixturesAdapter.OnClickListener {
            viewModel.onPlayMatch(it)
        })

        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        binding.rvFixtures.adapter = adapter

        viewModel.navigateToPlayMatch.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(FixturesFragmentDirections.actionFixturesFragmentToNewMatchFragment(it, viewModel.currentTournamentId))
                viewModel.doneNavigateToPlayMatch()
            }
        })

        viewModel.eventShowNextTourButton.observe(viewLifecycleOwner, Observer {
            binding.btnNextTour.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.eventShowErrorMessage.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(requireContext(), resources.getString(R.string.not_all_matches_played), Toast.LENGTH_SHORT).show()
                viewModel.doneShowErrorMessage()
            }
        })

        viewModel.eventGoToNextTour.observe(viewLifecycleOwner, Observer {
            binding.btnNextTour.isEnabled = !it
        })

        viewModel.fixtures.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                with(binding) {
                    btnNextTour.text = getString(R.string.tournament_over)
                    btnNextTour.isEnabled = false
                    rvFixtures.visibility = View.GONE
                    ivCompleteTournament.visibility = View.VISIBLE
                }
            } else if (it.size == 1) {
                binding.btnNextTour.text = getString(R.string.complete_tournament)
            }
        })

        return binding.root
    }

}
