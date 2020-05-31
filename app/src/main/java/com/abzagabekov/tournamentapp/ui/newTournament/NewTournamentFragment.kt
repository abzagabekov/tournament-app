package com.abzagabekov.tournamentapp.ui.newTournament

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.abzagabekov.tournamentapp.App

import com.abzagabekov.tournamentapp.R
import com.abzagabekov.tournamentapp.databinding.NewTournamentFragmentBinding
import com.abzagabekov.tournamentapp.di.AppComponent
import com.abzagabekov.tournamentapp.ui.ViewModelFactory
import java.lang.Exception
import javax.inject.Inject


/**
 * Created by abzagabekov on 05.05.2020.
 * email: abzagabekov@gmail.com
 */

class NewTournamentFragment : Fragment(), AdapterView.OnItemSelectedListener {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: NewTournamentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = NewTournamentFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this

        App.appComponent.inject(this)

        viewModel = ViewModelProvider(this, viewModelFactory).get(NewTournamentViewModel::class.java)

        viewModel.initViewModel(resources)

        binding.viewModel = viewModel

        initSpinner(binding.spTourType)

        viewModel.eventCreateNewTournament.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
                try {
                    val tournamentName = binding.etTourName.text.toString().trim()
                    val teamsCount = binding.etTeamsCount.text.toString().toInt()
                    viewModel.createNewTournament(tournamentName, teamsCount, binding.cbTwoLeg.isChecked)
                } catch (e: Exception) {
                    e.printStackTrace()
                    viewModel.showErrorMessage(InputErrorCodes.EMPTY_FIELDS)
                }

            } else {
                binding.progressBar.visibility = View.GONE
            }
        })

        viewModel.navigateToTournamentMenu.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NewTournamentFragmentDirections.actionNewTournamentFragmentToTournamentFragment(it))
                viewModel.doneNavigateToTournamentMenu()
            }
        })

        viewModel.eventShowErrorMessage.observe(viewLifecycleOwner, Observer {
            when (it) {
                InputErrorCodes.EMPTY_FIELDS -> {
                    binding.tvError.text = resources.getString(R.string.error_check_inputs)
                    binding.tvError.visibility = View.VISIBLE
                    viewModel.doneShowErrorMessage()
                }
                InputErrorCodes.INVALID_TEAMS_COUNT -> {
                    binding.tvError.text = resources.getString(R.string.error_invalid_teams_count)
                    binding.tvError.visibility = View.VISIBLE
                    viewModel.doneShowErrorMessage()
                }
            }
        })

        return binding.root

    }

    private fun initSpinner(spinner: Spinner) {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.tournament_types_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            spinner.onItemSelectedListener = this
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        viewModel.onChangeTournamentType(p0?.getItemAtPosition(position) as String)
    }


}
