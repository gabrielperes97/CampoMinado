package com.leopoldino.gabriel.campominado.game

interface MinefieldInterface {

    fun tickCounter(seconds: Int)

    fun markAsBomb(x: Int, y:Int)

    fun open(x: Int, y:Int, numBombsNear: Int)

    fun bomb(x: Int, y:Int)

    fun gameover()

    fun win()

    fun close(x: Int, y:Int)
}