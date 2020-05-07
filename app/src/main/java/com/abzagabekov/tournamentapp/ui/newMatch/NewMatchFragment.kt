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
import com.abzagabekov.tournamentapp.databinding.NewMatchFragmentBinding
import com.abzagabekov.tournamentapp.getTeams
import com.abzagabekov.tournamentapp.pojo.Team
import com.abzagabekov.tournamentapp.ui.newTournament.NewTournamentFragmentDirections
import kotlinx.android.synthetic.main.new_match_fragment.*

/**
 * Created by abzagabekov on 05.05.2020.
 * email: abzagabekov@gmail.com
 */

class NewMatchFragment : Fragment() {

    lateinit var viewModel: NewMatchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = NewMatchFragmentBinding.inflate(inflater)

        viewModel = ViewModelProvider(this).get(NewMatchViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        initSpinner(binding.spFirstTeam)
        initSpinner(binding.spSecondTeam)

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

    private fun initSpinner(spinner: Spinner) {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            viewModel.teams
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

    }


}
