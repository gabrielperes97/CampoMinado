package com.leopoldino.gabriel.campominado

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import java.util.*
import kotlin.collections.ArrayList

class GameActivity: AppCompatActivity() {

    enum class DIFFICULT {
        EASY,
        MEDIUM,
        HARD
    }

    lateinit var difficult : DIFFICULT

    lateinit var grid : TableLayout

    inner class Ponto(val x: Int, val y : Int) : Button(this) {
        var bombasProximas = 0
        set(value) {
            field = value
            if (text != "X")
                text = value.toString()
        }
        var bomba = false
        set(value) {
            field = value
            if (value)
                text = "X"
        }

        init {
            super.setOnClickListener{view ->
                val clickedButton = view as Ponto
                if (!clickedButton.bomba)
                {
                    //Abre os sem bomba juntos
                    //ou abre numero
                }
                else
                {
                    //game over
                }

            }
        }
    }

    lateinit var campo : Array<Array<Ponto>>
    lateinit var bombas : Array<Ponto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        difficult = intent.getSerializableExtra("Difficult") as DIFFICULT
        grid = findViewById(R.id.tl_game_grid)

        campo = when(difficult){
            DIFFICULT.EASY -> makeButtons(7)
            DIFFICULT.MEDIUM -> makeButtons(10)
            DIFFICULT.HARD -> makeButtons(13)
        }

        bombas = when(difficult){
            DIFFICULT.EASY -> putBombs(7, 7)
            DIFFICULT.MEDIUM -> putBombs(10, 10)
            DIFFICULT.HARD -> putBombs(13, 13)
        }


    }

    fun makeButtons(size : Int)  : Array<Array<Ponto>>{

        grid.removeAllViewsInLayout()

        val list = Array<Array<Ponto>>(size){i : Int ->

            val row = TableRow(this)
            val row_layout = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT)
            row_layout.setMargins(0, -9, 0, -3)
            row.layoutParams = row_layout

            val pontos = Array<Ponto>(size){j : Int ->
                val button = Ponto(i, j)
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


                button
            }
            grid.addView(row)

            pontos
        }

        return list
    }

    fun putBombs(many : Int, bound : Int) : Array<Ponto> {
        val random = Random()
        val bombs = Array<Ponto>(many) {

            var x = 0
            var y = 0
            do {
                x = random.nextInt(bound)
                y = random.nextInt(bound)
            } while (campo[x][y].bomba)
            campo[x][y].bomba = true

            //Cima
            if (x-1 >= 0)
            {
                //Diagonal superior esqueda
                if (y-1 >= 0)
                    campo[x-1][y-1].bombasProximas++
                //Superior
                campo[x-1][y].bombasProximas++
                //Diagonal superior direita
                if (y+1 < bound)
                    campo[x-1][y+1].bombasProximas++
            }
            // --Mesma linha--
            //esquerda
            if (y-1 >= 0)
                campo[x][y-1].bombasProximas++
            //direita
            if (y+1 < bound)
                campo[x][y+1].bombasProximas++

            //Baixo
            if (x+1 < bound)
            {
                //Diagonal inferior esqueda
                if (y-1 >= 0)
                    campo[x+1][y-1].bombasProximas++
                //Superior
                campo[x+1][y].bombasProximas++
                //Diagonal inferior direita
                if (y+1 < bound)
                    campo[x+1][y+1].bombasProximas++
            }
             campo[x][y]
        }

        return bombs
    }
}