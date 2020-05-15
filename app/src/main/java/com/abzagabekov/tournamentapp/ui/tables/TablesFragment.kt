package com.abzagabekov.tournamentapp.ui.tables

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

import com.abzagabekov.tournamentapp.R
import com.abzagabekov.tournamentapp.databinding.TablesFragmentBinding
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.tables_fragment.*
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Created by abzagabekov on 05.05.2020.
 * email: abzagabekov@gmail.com
 */

class TablesFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = TablesFragmentBinding.inflate(inflater)

        return binding.root
    }






}
