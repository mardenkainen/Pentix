package com.example.pentix.activities

import android.graphics.drawable.AnimatedVectorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.example.pentix.views.FieldView
import com.example.pentix.R
import com.example.pentix.game.Pentix
import com.example.pentix.game.FigureType
import com.example.pentix.storage.AppPreferences
import com.example.pentix.views.FigureView

class PentixActivity : AppCompatActivity() {
    private var preferences:AppPreferences?=null
    private var pentixView:FieldView?=null
    private var currentScoreView:TextView?=null
    private var recordScoreView:TextView?=null
    private var levelView:TextView?=null
    private var mirror:ImageButton?=null
    private var score=0
    private var record=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pentex)
        preferences=AppPreferences(this)
        record=preferences!!.getHighScore()
        mirror=findViewById(R.id.mirror)
        val nextFigureView=findViewById<FigureView>(R.id.nextFigure)
        val figureHolder=findViewById<FigureView>(R.id.holder)
        findViews()
        recordScoreView!!.text="$record"
        setLevelText(0)
        updateScore(0)
        mirror!!.setOnClickListener {
            pentixView!!.fieldMove(Pentix.Motions.MIRROR)
            pressAnimation()
        }

        pentixView!!.setGameListener(object: Pentix.GameActionListener{
            private var lines=0
            private var level=0
            override fun onFigureTouchedGround(nextFigureType: FigureType) {
                nextFigureView.setFigure(nextFigureType)

            }
            override fun onRowsFilled(numOfFilledRows: Int) {
                lines+=numOfFilledRows
                updateScore(numOfFilledRows)
                if (lines>=8){
                    level++
                    pentixView!!.increaseGameSpeed()
                    setLevelText(level)
                    lines %= 8
                }
            }

            override fun gameOver() {
                pentixView!!.finish()
                val dialog= GameOverDialog(this@PentixActivity,score)
                dialog.show()
            }

        })

        figureHolder.setOnClickListener {
            figureHolder.setFigure(pentixView!!.saveFigure())
        }
        pentixView!!.start()
    }

    private fun pressAnimation() {
        mirror!!.setBackgroundResource(R.drawable.avd_mirror_btn_animation)
        val  avdPress=mirror!!.background as AnimatedVectorDrawable
        avdPress.start()
    }

    private fun setLevelText(level:Int){
        levelView!!.text=getString(R.string.level,level)
    }

    private fun updateScore(filledRows:Int){
        score+=calculateScore(filledRows)
        currentScoreView!!.text="$score"
        if (record<=score){
            recordScoreView!!.text="$score"
            preferences!!.saveScore(score)
        }
    }

    private fun calculateScore(filledRows:Int): Int {
        var scores=0
        for (i in 0 until filledRows){
            scores=scores*2+100
        }
        return scores
    }

    private fun findViews(){
        currentScoreView=findViewById(R.id.score)
        recordScoreView=findViewById(R.id.record)
        pentixView=findViewById(R.id.gameView)
        levelView=findViewById(R.id.level)
    }

    override fun onPause() {
        super.onPause()
        pentixView!!.pause()
    }



    override fun onResume() {
        super.onResume()
        pentixView!!.start()
    }
}