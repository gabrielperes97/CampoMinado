package com.leopoldino.gabriel.campominado

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow

class GameActivity: AppCompatActivity() {

    enum class DIFFICULT {
        EASY,
        MEDIUM,
        HARD
    }

    lateinit var difficult : DIFFICULT

    lateinit var grid : TableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        difficult = intent.getSerializableExtra("Difficult") as DIFFICULT
        grid = findViewById(R.id.tl_game_grid)

        when(difficult){
            DIFFICULT.EASY -> makeButtons(7)
            DIFFICULT.MEDIUM -> makeButtons(10)
            DIFFICULT.HARD -> makeButtons(13)
        }


    }

    fun makeButtons(size : Int){

        grid.removeAllViewsInLayout()

        for (i in 1..size) {
            val row = TableRow(this)
            val row_layout = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT)
            row_layout.setMargins(0, -9, 0, -3)
            row.layoutParams = row_layout

            for (j in 1..size){
                val button = Button(this)
                val layout = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
                layout.weight = 1f

                //If button is not the last
                if (j < size)
                {
                    layout.setMargins(0, 0, -7, 0)
                    layout.marginEnd = -7
                }


                button.layoutParams = layout

                button.text = "0"
                row.addView(button)
            }
            grid.addView(row)
        }

    }
}