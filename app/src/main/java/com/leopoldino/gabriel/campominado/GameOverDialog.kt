package com.leopoldino.gabriel.campominado

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class GameOverDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.fragment_gameover, null)

        val text = arguments.getCharSequence("text")

        val tvGameOver = view.findViewById<TextView>(R.id.tvGameOver)
        tvGameOver.text = text

        val okButton = view.findViewById<Button>(R.id.btOk)
        okButton.setOnClickListener {
            dismiss()
            activity.finish()
        }

        val builder = AlertDialog.Builder(activity)
        builder.setView(view)
        return builder.create()
    }


}