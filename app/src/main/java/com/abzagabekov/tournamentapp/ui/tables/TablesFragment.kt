package com.abzagabekov.tournamentapp.ui.tables

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.abzagabekov.tournamentapp.R

/**
 * Created by abzagabekov on 05.05.2020.
 * email: abzagabekov@gmail.com
 */

class TablesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tables_fragment, container, false)
    }

}