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

class NewTeamDialogFragment(private val hostFragment: TeamsFragment) : DialogFragment() {

    private var listener: NewTeamDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)

        val inflater = activity?.layoutInflater

        val binding = DialogNewTeamBinding.inflate(inflater!!)

        builder.setView(binding.root)
            .setPositiveButton(R.string.add) { dialog, which ->
                val teamName = binding.etTeamName.text.toString().trim()
                if (teamName.isEmpty()) {
                    dismiss()
                } else {
                    listener?.onDialogPositiveClick(teamName)
                }
            }
            .setNegativeButton(R.string.cancel) { dialog, which ->
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



    interface NewTeamDialogListener {
        fun onDialogPositiveClick(teamName: String)
    }
}