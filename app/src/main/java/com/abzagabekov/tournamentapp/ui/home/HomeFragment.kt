package com.abzagabekov.tournamentapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.abzagabekov.tournamentapp.R
import com.abzagabekov.tournamentapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentHomeBinding.inflate(inflater)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.viewModel = homeViewModel
        binding.lifecycleOwner = this

        val adapter = TournamentsAdapter(TournamentsAdapter.OnClickListener {
            homeViewModel.displayTournamentMenu(it)
        })

        homeViewModel.navigateToSelectedTournament.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(HomeFragmentDirections.actionNavHomeToTournamentFragment())
                homeViewModel.displayTournamentMenuComplete()
            }
        })

        binding.rvTournaments.adapter = adapter

        return binding.root
    }
}
