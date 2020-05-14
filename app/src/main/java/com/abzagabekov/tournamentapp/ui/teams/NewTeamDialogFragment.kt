package com.abzagabekov.tournamentapp.ui.teams

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.abzagabekov.tournamentapp.R
import com.abzagabekov.tournamentapp.databinding.DialogNewTeamBinding
import java.lang.ClassCastException
import java.lang.Exception
import java.lang.StringBuilder

/**
 * Created by abzagabekov on 07.05.2020.
 * email: abzagabekov@gmail.com
 */

class NewTeamDialogFragment() : DialogFragment() {

    private var listener: NewTeamDialogListener? = null
    private var hostFragment: TeamsFragment? = null
    private var teamName: String? = null

    companion object {

        fun newInstance(hostFragment: TeamsFragment, teamName: String): NewTeamDialogFragment {
            val fragment = NewTeamDialogFragment()
            fragment.hostFragment = hostFragment
            fragment.teamName = teamName
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)

        val inflater = activity?.layoutInflater

        val binding = DialogNewTeamBinding.inflate(inflater!!)

        binding.etTeamName.setText(teamName)

        builder.setView(binding.root)
            .setPositiveButton(R.string.add) { _, _ ->
                val teamName = binding.etTeamName.text.toString().trim()
                if (teamName.isEmpty()) {
                    dismiss()
                } else {
                    listener?.onDialogPositiveClick(teamName)
                }
            }
            .setNegativeButton(R.string.cancel) { _, _ ->
                dismiss()
            }

        return builder.create()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = hostFragment
        } catch (e: ClassCastException) {
            throw ClassCastException("$hostFragment must implement NewTeamDialogListener")
        }
    }

    override fun onStop() {
        super.onStop()
        dismissAllowingStateLoss()
    }

    interface NewTeamDialogListener {
        fun onDialogPositiveClick(teamName: String)
    }
}