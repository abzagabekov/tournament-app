package com.abzagabekov.tournamentapp.ui.tables

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.abzagabekov.tournamentapp.*

import com.abzagabekov.tournamentapp.databinding.TablesFragmentBinding
import com.abzagabekov.tournamentapp.di.InjectingSavedStateViewModelFactory
import com.abzagabekov.tournamentapp.pojo.KnockoutNode
import com.abzagabekov.tournamentapp.pojo.ResultTable
import com.abzagabekov.tournamentapp.ui.ViewModelFactory
import de.blox.graphview.Graph
import de.blox.graphview.GraphView
import de.blox.graphview.Node
import de.blox.graphview.ViewHolder
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.sample_pos_table_row.view.*
import kotlinx.android.synthetic.main.sample_short_table_row.view.*
import kotlinx.android.synthetic.main.tables_fragment.*
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Created by abzagabekov on 05.05.2020.
 * email: abzagabekov@gmail.com
 */

class TablesFragment : Fragment() {

    @Inject lateinit var abstractFactory: InjectingSavedStateViewModelFactory
    private lateinit var viewModel: TablesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = TablesFragmentBinding.inflate(inflater)

        val arguments = TablesFragmentArgs.fromBundle(requireArguments())

        val defArgs = bundleOf(TablesViewModel.KEY_TOURNAMENT_ID to arguments.tournamentId,
            TablesViewModel.KEY_TOURNAMENT_TYPE to arguments.tournamentType,
            TablesViewModel.KEY_TYPE_LEAGUE to resources.getStringArray(R.array.tournament_types_array)[TYPE_LEAGUE],
            TablesViewModel.KEY_TYPE_KNOCKOUT to resources.getStringArray(R.array.tournament_types_array)[TYPE_KNOCKOUT])

        App.appComponent.inject(this)

        val factory = abstractFactory.create(this, defArgs)

        viewModel = ViewModelProvider(this, factory).get(TablesViewModel::class.java)

        viewModel.eventShowResultTable.observe(viewLifecycleOwner, Observer {
            showResultTable(it, inflater, container, binding)
        })

        viewModel.eventShowResultGraph.observe(viewLifecycleOwner, Observer {

        })

        if (arguments.tournamentType == resources.getStringArray(R.array.tournament_types_array)[TYPE_KNOCKOUT]) {
            binding.tableOverview.visibility = View.GONE
            binding.graph.visibility = View.VISIBLE
        }

        return binding.root
    }

    private fun showResultTable(results: List<ResultTable>,
                                inflater: LayoutInflater,
                                container: ViewGroup?,
                                binding: TablesFragmentBinding) {
        results.forEachIndexed{ _, it ->
            val newRow =
                inflater.inflate(R.layout.sample_short_table_row, container, false) as TableRow
            newRow.tv_team_item.text = it.team
            newRow.tv_games_item.text = it.games.toString()
            newRow.tv_gd_item.text = it.goalsDifference.toString()
            newRow.tv_points_item.text = it.points.toString()
            binding.tableOverview.addView(newRow)
        }
    }

    private fun showResultGraph(results: List<KnockoutNode>, binding: TablesFragmentBinding) {
        val nodes = mutableMapOf<Long, Node>()
        for (i in results.indices) {
            nodes[results[i].parent!!] = Node(results[i].name)
        }

        val graph = Graph()
        for (i in 1 until nodes.size) {

        }
    }

    class NodesViewHolder(itemView: View) : ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.tv_node)
    }

}
