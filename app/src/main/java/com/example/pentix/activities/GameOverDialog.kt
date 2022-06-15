package com.example.pentix.activities

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.widget.Button
import android.widget.TextView
import com.example.pentix.R

class GameOverDialog(context: Context,score:Int) : Dialog(context) {
    var scoreView:TextView?=null
    init {
        this.setCancelable(false)
        this.setContentView(R.layout.game_over_dialog)
        scoreView=findViewById(R.id.scoreView)
        setScore(score)
        val  backBtn=findViewById<Button>(R.id.back)
        backBtn.setOnClickListener {
            (context as? Activity)!!.finish()
        }
    }

    fun setScore(score: Int){
        scoreView!!.text=context.getString(R.string.score_message,score)
    }
}