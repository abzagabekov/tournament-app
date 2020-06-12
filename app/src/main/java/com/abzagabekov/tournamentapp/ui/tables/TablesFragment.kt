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
import com.abzagabekov.tournamentapp.R

import com.abzagabekov.tournamentapp.databinding.TablesFragmentBinding
import com.abzagabekov.tournamentapp.di.InjectingSavedStateViewModelFactory
import com.abzagabekov.tournamentapp.pojo.KnockoutNode
import com.abzagabekov.tournamentapp.pojo.ResultTable
import com.abzagabekov.tournamentapp.ui.ViewModelFactory
import de.blox.graphview.*
import de.blox.graphview.tree.BuchheimWalkerAlgorithm
import de.blox.graphview.tree.BuchheimWalkerConfiguration
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

    private var graph: Graph? = null

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
            showResultGraph(it, graph!!)
        })

        if (arguments.tournamentType == resources.getStringArray(R.array.tournament_types_array)[TYPE_KNOCKOUT]) {
            binding.tableOverview.visibility = View.GONE
            binding.graph.visibility = View.VISIBLE

            initGraphAdapter(binding)

        } else {
            binding.tableOverview.visibility = View.VISIBLE
            binding.graph.visibility = View.GONE
        }

        return binding.root
    }

    private fun initGraphAdapter(binding: TablesFragmentBinding) {

        graph = Graph()

        val adapter = object : BaseGraphAdapter<ViewHolder>(graph!!) {
            override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
                val view =
                    LayoutInflater.from(parent?.context).inflate(R.layout.node, parent, false)
                return NodesViewHolder(view)
            }

            override fun onBindViewHolder(viewHolder: ViewHolder?, data: Any?, position: Int) {
                (viewHolder as NodesViewHolder).textView.text = data.toString().trim()
            }
        }

        binding.graph.adapter = adapter

        val config = BuchheimWalkerConfiguration.Builder()
            .setSiblingSeparation(100)
            .setLevelSeparation(200)
            .setSubtreeSeparation(200)
            .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
            .build()

        adapter.algorithm = BuchheimWalkerAlgorithm(config)
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

    private fun showResultGraph(results: List<KnockoutNode>, graph: Graph) {
        val nodes = mutableMapOf<Long, Node>()
        nodes[results[0].id] = Node(results[0].name)

        for (i in 1 until results.size) {
            nodes[results[i].id] = Node(results[i].name)
            graph.addEdge(nodes[results[i].parent]!!, nodes[results[i].id]!!)
        }
    }

    private fun showResultGraphTest(binding: TablesFragmentBinding) {
        var nodeCount = 1
        val graph = Graph()
        val node1 = Node("Node " + nodeCount++)
        val node2 = Node("Node " + nodeCount++)
        val node3 = Node("Node " + nodeCount++)
        val node4 = Node("Node " + nodeCount++)
        val node5 = Node("Node " + nodeCount++)
        val node6 = Node("Node " + nodeCount++)
        val node7 = Node("Node " + nodeCount++)
        val node8 = Node("Node " + nodeCount++)
        val node9 = Node("Node " + nodeCount++)
        val node10 = Node("Node " + nodeCount++)
        val node11 = Node("Node " + nodeCount++)
        val node12 = Node("Node " + nodeCount++)
        val node13 = Node("Node " + nodeCount++)
        val node14 = Node("Node " + nodeCount++)
        val node15 = Node("Node " + nodeCount++)

        graph.addEdge(node1, node2)
        graph.addEdge(node1, node3)
        graph.addEdge(node2, node4)
        graph.addEdge(node2, node5)
        graph.addEdge(node3, node6)
        graph.addEdge(node3, node7)
        graph.addEdge(node4, node8)
        graph.addEdge(node4, node9)
        graph.addEdge(node5, node10)
        graph.addEdge(node5, node11)
        graph.addEdge(node6, node12)
        graph.addEdge(node6, node13)
        graph.addEdge(node7, node14)
        graph.addEdge(node7, node15)

        val adapter = object : BaseGraphAdapter<ViewHolder>(graph) {
            override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
                val view = LayoutInflater.from(parent?.context).inflate(R.layout.node, parent, false)
                return NodesViewHolder(view)
            }

            override fun onBindViewHolder(viewHolder: ViewHolder?, data: Any?, position: Int) {
                (viewHolder as NodesViewHolder).textView.text = data.toString().trim()
            }
        }

        binding.graph.adapter = adapter

        val config = BuchheimWalkerConfiguration.Builder()
            .setSiblingSeparation(100)
            .setLevelSeparation(200)
            .setSubtreeSeparation(200)
            .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
            .build()

        adapter.algorithm = BuchheimWalkerAlgorithm(config)

    }

    class NodesViewHolder(itemView: View) : ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.tv_node)
    }

}
