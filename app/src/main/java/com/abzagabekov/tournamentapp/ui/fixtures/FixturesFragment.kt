package com.abzagabekov.tournamentapp.ui.fixtures

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.abzagabekov.tournamentapp.R
import com.abzagabekov.tournamentapp.databinding.FixturesFragmentBinding

/**
 * Created by abzagabekov on 05.05.2020.
 * email: abzagabekov@gmail.com
 */

class FixturesFragment : Fragment() {

    lateinit var viewModel: FixturesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FixturesFragmentBinding.inflate(inflater)

        viewModel = ViewModelProvider(this).get(FixturesViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.rvFixtures.adapter = FixturesAdapter(FixturesAdapter.OnClickListener {
            viewModel.onPlayMatch()
        })

        viewModel.navigateToPlayMatch.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigate(FixturesFragmentDirections.actionFixturesFragmentToNewMatchFragment())
                viewModel.doneNavigateToPlayMatch()
            }
        })

        return binding.root
    }

}
