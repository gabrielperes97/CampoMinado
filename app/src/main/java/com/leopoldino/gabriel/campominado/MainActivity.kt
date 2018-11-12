package com.leopoldino.gabriel.campominado

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var btEasy : Button
    lateinit var  btMedium : Button
    lateinit var btHard : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btEasy = findViewById(R.id.bt_easy)
        btMedium = findViewById(R.id.bt_medium)
        btHard = findViewById(R.id.bt_hard)

        btEasy.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("Difficult", GameActivity.DIFFICULT.EASY)
            startActivity(intent)
        }

        btMedium.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("Difficult", GameActivity.DIFFICULT.MEDIUM)
            startActivity(intent)
        }

        btHard.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("Difficult", GameActivity.DIFFICULT.HARD)
            startActivity(intent)
        }
    }
}
