package com.leopoldino.gabriel.campominado

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.*
import com.leopoldino.gabriel.campominado.game.Minefield
import com.leopoldino.gabriel.campominado.game.MinefieldInterface

class GameActivity : AppCompatActivity() {

    enum class DIFFICULT {
        EASY,
        MEDIUM,
        HARD
    }

    lateinit var difficult : DIFFICULT

    lateinit var grid : TableLayout

    lateinit var table : Array<Array<Field>>
    lateinit var tvCounter : TextView
    lateinit var btReset : Button

    lateinit var game : Minefield

    val gameInterface = object : MinefieldInterface{
        override fun close(x: Int, y: Int) {
            runOnUiThread { table[x][y].text = "" }
        }

        override fun tickCounter(seconds: Int) {
            runOnUiThread { tvCounter.text = seconds.toString() }
        }

        override fun markAsBomb(x: Int, y: Int) {
            runOnUiThread { table[x][y].text = "X" }
        }

        override fun open(x: Int, y: Int, numBombsNear: Int) {
            runOnUiThread { table[x][y].text = numBombsNear.toString() }
        }

        override fun bomb(x: Int, y: Int) {
            runOnUiThread { table[x][y].text = "B" }
        }

        override fun gameover() {
            runOnUiThread {
                val gameOverDialog = GameOverDialog()
                val bundle = Bundle()
                bundle.putCharSequence("text", "Game Over")
                gameOverDialog.arguments = bundle
                gameOverDialog.show(fragmentManager, "")

                btReset.text = "X("
            }
        }

        override fun win() {
            runOnUiThread {
                val gameOverDialog = GameOverDialog()
                val bundle = Bundle()
                bundle.putCharSequence("text", "Você Venceu \\o/")
                gameOverDialog.arguments = bundle
                gameOverDialog.show(fragmentManager, "")

                btReset.text = ":)"
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        difficult = intent.getSerializableExtra("Difficult") as DIFFICULT
        grid = findViewById(R.id.tl_game_grid)
        btReset = findViewById(R.id.btReset)
        tvCounter = findViewById(R.id.tv_timer)

        makeScreen()

        game = when(difficult){
            DIFFICULT.EASY -> Minefield(size = 7, numBombs = 7, ui = gameInterface)
            DIFFICULT.MEDIUM -> Minefield(size = 10, numBombs = 10, ui = gameInterface)
            DIFFICULT.HARD -> Minefield(size = 10, numBombs = 15, ui = gameInterface)
        }

        btReset.setOnClickListener {
            game.reset()
            makeScreen()
        }

    }

    private fun makeScreen() {
        table = when(difficult){
            DIFFICULT.EASY -> makeButtons(7)
            DIFFICULT.MEDIUM -> makeButtons(10)
            DIFFICULT.HARD -> makeButtons(10)
        }
        btReset.text = ":o"

    }

    private fun makeButtons(size : Int)  : Array<Array<Field>>{

        grid.removeAllViewsInLayout()

        val list = Array<Array<Field>>(size){i : Int ->

            val row = TableRow(this)
            val row_layout = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT)
            row_layout.setMargins(0, -9, 0, -3)
            row.layoutParams = row_layout

            val pontos = Array<Field>(size){j : Int ->
                val button = Field(i, j)
                val layout = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
                layout.weight = 1f

                //If button is not the last
                if (j < size)
                {
                    layout.setMargins(0, 0, -7, 0)
                    layout.marginEnd = -7
                }


                button.layoutParams = layout

                button.text = ""
                row.addView(button)


                button
            }
            grid.addView(row)

            pontos
        }

        return list
    }

    inner class Field (val x: Int, val y:Int) : Button(this)
    {
        init {
            super.setOnClickListener{
                game.click(x, y)
            }

            super.setLongClickable(true)
            super.setOnLongClickListener{
                game.markAsBomb(x, y)
                true
            }
        }
    }
}