package com.leopoldino.gabriel.campominado.game

class Field(val x:Int, val y:Int) {

    /**
     * OPENED = The field has clicked, it's open
     * CLOSED = The field hasn't clicked yet
     * MARKED = The field is marked as bomb
     * BLOWED = The field had a bomb and has clicked
     * BOMB = The game is over, this field had a bomb ans hasn't clicked
     */
    enum class Status { OPENED, CLOSED, MARKED, BLOWED, BOMB }

    //Start as closed
    var status = Status.CLOSED

    var bomb: Boolean = false

    var bombsNear = 0




}