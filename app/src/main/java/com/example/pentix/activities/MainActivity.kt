package com.example.pentix.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.pentix.R
import com.example.pentix.storage.AppPreferences
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    var highScore:TextView?=null
    private var preferences:AppPreferences?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preferences=AppPreferences(this)

        val btnNewGame=findViewById<Button>(R.id.new_game)
        val btnResetScore=findViewById<Button>(R.id.reset_score)
        val btnExit=findViewById<Button>(R.id.exit_game)

        highScore=findViewById(R.id.high_score)
        printHighScore()

        btnNewGame.setOnClickListener(this::newGameClick)
        btnResetScore.setOnClickListener(this::resetScoreClick)
        btnExit.setOnClickListener(this::exitClick)
    }

    private  fun newGameClick(view: View){
        val intent= Intent(this, PentixActivity::class.java)
        startActivity(intent)
    }

    private fun exitClick(view: View){
        exitProcess(0)
    }

    private fun resetScoreClick(view: View){
        val preferences=AppPreferences(this)
        preferences.clearHighScore()
        printHighScore()
    }

    override fun onResume() {
        super.onResume()
        printHighScore()
    }

    private fun printHighScore(){
        highScore!!.text= getString(R.string.high_score_default,preferences!!.getHighScore())
    }
}