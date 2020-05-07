package com.abzagabekov.tournamentapp.ui.teams

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.abzagabekov.tournamentapp.App

import com.abzagabekov.tournamentapp.R
import com.abzagabekov.tournamentapp.databinding.FragmentTeamsBinding
import com.abzagabekov.tournamentapp.ui.ViewModelFactory
import javax.inject.Inject

/**
 * Created by abzagabekov on 07.05.2020.
 * email: abzagabekov@gmail.com
 */

class TeamsFragment : Fragment(), NewTeamDialogFragment.NewTeamDialogListener {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: TeamsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentTeamsBinding.inflate(inflater)

        App.appComponent.inject(this)

        viewModel = ViewModelProvider(this, viewModelFactory).get(TeamsViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel= viewModel

        val arguments = TeamsFragmentArgs.fromBundle(requireArguments())
        viewModel.tournamentId = arguments.tournamentId

        binding.rvTeams.adapter = TeamsAdapter(TeamsAdapter.OnClickListener {})

        viewModel.eventShowNewTeamDialog.observe(viewLifecycleOwner, Observer {
            if (it) {
                val dialog = NewTeamDialogFragment(this)
                dialog.show(parentFragmentManager, "NewTeamDialogFragment")
                viewModel.showNewTeamDialogComplete()
            }
        })

        return binding.root
    }

    override fun onDialogPositiveClick(teamName: String) {
        viewModel.addNewTeam(teamName)
    }

}
