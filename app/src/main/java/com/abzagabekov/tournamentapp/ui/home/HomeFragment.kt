package com.abzagabekov.tournamentapp.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.abzagabekov.tournamentapp.App
import com.abzagabekov.tournamentapp.R
import com.abzagabekov.tournamentapp.databinding.FragmentHomeBinding
import com.abzagabekov.tournamentapp.ui.ViewModelFactory
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentHomeBinding.inflate(inflater)

        binding.lifecycleOwner = this

        App.appComponent.inject(this)

        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        binding.viewModel = homeViewModel

        val adapter = TournamentsAdapter(TournamentsAdapter.OnClickListener {
            homeViewModel.displayTournamentMenu(it)
        })

        binding.rvTournaments.adapter = adapter

        homeViewModel.navigateToSelectedTournament.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(HomeFragmentDirections.actionNavHomeToTournamentFragment(it))
                homeViewModel.displayTournamentMenuComplete()
            }
        })

        homeViewModel.navigateToNewTournament.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(HomeFragmentDirections.actionNavHomeToNewTournamentFragment())
                homeViewModel.doneNavigateToNewTournament()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
