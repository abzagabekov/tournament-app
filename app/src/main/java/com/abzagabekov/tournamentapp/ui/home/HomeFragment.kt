package com.abzagabekov.tournamentapp.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.DividerItemDecoration
import com.abzagabekov.tournamentapp.App
import com.abzagabekov.tournamentapp.R
import com.abzagabekov.tournamentapp.TournamentKeyProvider
import com.abzagabekov.tournamentapp.TournamentLookup
import com.abzagabekov.tournamentapp.databinding.FragmentHomeBinding
import com.abzagabekov.tournamentapp.pojo.Tournament
import com.abzagabekov.tournamentapp.ui.ViewModelFactory
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var homeViewModel: HomeViewModel

    private var actionMode: ActionMode? = null
    private var tournaments: List<Tournament>? = null

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
        //binding.rvTournaments.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

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

        homeViewModel.tournaments.observe(viewLifecycleOwner, Observer {
            val tracker = SelectionTracker
                .Builder<Tournament>(
                    "selection-1",
                    binding.rvTournaments,
                    TournamentKeyProvider(it, binding.rvTournaments),
                    TournamentLookup(binding.rvTournaments),
                    StorageStrategy.createParcelableStorage(Tournament::class.java)
                ).withSelectionPredicate(
                    SelectionPredicates.createSelectAnything()
                ).build()

            tracker.addObserver(object : SelectionTracker.SelectionObserver<Tournament>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    if (tracker.hasSelection() && actionMode == null) {
                        actionMode = requireActivity().startActionMode(ActionModeController(tracker))
                        setSelectedTitle(tracker.selection.size())

                    } else if (!tracker.hasSelection()) {
                        actionMode?.finish()
                        actionMode = null
                    } else {
                        setSelectedTitle(tracker.selection.size())
                    }
                }
            })

            adapter.tracker = tracker
            tournaments = it
        })

        return binding.root
    }



    private fun setSelectedTitle(selected: Int) {
        actionMode?.title = "Selected: $selected"
    }

    inner class ActionModeController(
        private val tracker: SelectionTracker<Tournament>
    ) : ActionMode.Callback {

        private var isAllSelected = false

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            return when(item?.itemId) {
                R.id.action_delete -> {

                    true
                }
                R.id.action_select_all -> {
                    selectAllItems()
                }
                else -> false
            }
        }

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.menuInflater?.inflate(R.menu.menu_action_mode, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return true
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            tracker.clearSelection()
        }

        private fun selectAllItems(): Boolean {
            tournaments?.forEach {
                if (!isAllSelected) tracker.select(it) else
                    tracker.deselect(it)
            }
            isAllSelected = !isAllSelected
            return true
        }

    }

}
