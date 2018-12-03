package com.leopoldino.gabriel.campominado.game

import java.util.*

class Minefield (val size: Int, val numBombs: Int, val ui : MinefieldInterface) {

    val bombs: Array<Field>
    var firstClick = true
    var countTime = 0
    lateinit var timer : Timer

    val table : Array<Array<Field>>
    val counterTask = object : TimerTask() {
        override fun run() {
            countTime += 1
            ui.tickCounter(countTime)
        }
    }

    init {
        table = Array<Array<Field>>(size){i: Int ->
            Array<Field>(size) { j: Int  ->
                Field(i,j)
            }
        }
        bombs = putBombs()
    }


    private fun putBombs() : Array<Field> {
        val random = Random()
        val bombs = Array<Field>(numBombs) {

            var x = 0
            var y = 0
            do {
                x = random.nextInt(size)
                y = random.nextInt(size)
            } while (table[x][y].bomb)
            table[x][y].bomb = true

            //Cima
            if (x-1 >= 0)
            {
                //Diagonal superior esqueda
                if (y-1 >= 0)
                    table[x-1][y-1].bombsNear++
                //Superior
                table[x-1][y].bombsNear++
                //Diagonal superior direita
                if (y+1 < size)
                    table[x-1][y+1].bombsNear++
            }
            // --Mesma linha--
            //esquerda
            if (y-1 >= 0)
                table[x][y-1].bombsNear++
            //direita
            if (y+1 < size)
                table[x][y+1].bombsNear++

            //Baixo
            if (x+1 < size)
            {
                //Diagonal inferior esqueda
                if (y-1 >= 0)
                    table[x+1][y-1].bombsNear++
                //Superior
                table[x+1][y].bombsNear++
                //Diagonal inferior direita
                if (y+1 < size)
                    table[x+1][y+1].bombsNear++
            }
            table[x][y]
        }

        return bombs
    }

    fun click(x : Int, y: Int)
    {
        if (firstClick) {
            startTimer()
            firstClick = false
        }

        if (!table[x][y].bomb)
        {
            backintrackOpen(x,y)
        }
        else
        {
            gameover()
        }
        testWin()
    }

    fun markAsBomb(x: Int, y: Int)
    {
        if (table[x][y].status == Field.Status.CLOSED) {
            table[x][y].status = Field.Status.MARKED
            ui.markAsBomb(x, y)
        }
        else{
            if (table[x][y].status == Field.Status.MARKED) {
                table[x][y].status = Field.Status.CLOSED
                ui.close(x, y)
            }
        }

    }

    private fun backintrackOpen(x: Int, y: Int)
    {
        val field = table[x][y]

        if (field.status != Field.Status.MARKED)
        {
            ui.open(x,y,field.bombsNear)
        }
        if (field.bombsNear == 0 && (field.status == Field.Status.CLOSED))
        {
            field.status = Field.Status.OPENED
            if (x-1 >= 0)
            {
                if (y-1 >= 0)
                    backintrackOpen(x-1, y-1)
                backintrackOpen(x-1, y)
                if (y+1 < table.size)
                    backintrackOpen(x-1, y+1)
            }
            if (y-1 >= 0)
                backintrackOpen(x, y-1)
            if (y+1 < table.size)
                backintrackOpen(x, y+1)
            if (x+1 < table.size)
            {
                if (y-1 >= 0)
                    backintrackOpen(x+1, y-1)
                backintrackOpen(x+1, y)
                if (y+1 < table.size)
                    backintrackOpen(x+1, y+1)
            }
        }
    }

    private fun testWin() {
        var winning = true

        //Se todas as bombas estão marcadas
        bombs.forEach {f ->
            if (f.status != Field.Status.MARKED)
                winning = false
        }

        //E  nenhum outro campo errado está marcado como bomba
        table.forEach { col ->
            col.forEach { f ->
                if (f.status == Field.Status.MARKED && !f.bomb)
                {
                    winning = false
                }
            }
        }
        if (winning)
        {
            win()
        }
    }

    private fun startTimer()
    {
        timer = Timer("Counter")
        countTime = 0
        timer.scheduleAtFixedRate(counterTask, 1000, 1000)
    }

    private fun stopTimer()
    {
        timer.cancel()
    }

    private fun gameover()
    {
        ui.gameover()
        stopTimer()
    }

    private fun win()
    {
        ui.win()
        stopTimer()
    }
}

