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

import com.abzagabekov.tournamentapp.R
import com.abzagabekov.tournamentapp.databinding.NewTournamentFragmentBinding


/**
 * Created by abzagabekov on 05.05.2020.
 * email: abzagabekov@gmail.com
 */

class NewTournamentFragment : Fragment(), AdapterView.OnItemSelectedListener {

    lateinit var viewModel: NewTournamentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = NewTournamentFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(NewTournamentViewModel::class.java)

        binding.viewModel = viewModel

        initSpinner(binding.spTourType)

        viewModel.eventCreateNewTournament.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })

        viewModel.navigateToTournamentMenu.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NewTournamentFragmentDirections.actionNewTournamentFragmentToTournamentFragment())
                viewModel.doneNavigateToTournamentMenu()
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

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        viewModel.tournamentType = p0?.getItemAtPosition(p2) as String
    }


}
