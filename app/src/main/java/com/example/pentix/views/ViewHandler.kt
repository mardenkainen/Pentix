package com.example.pentix.views

import android.os.Handler
import android.os.Looper
import com.example.pentix.game.Pentix


class ViewHandler(private val view: FieldView) {
    private val handler=Handler(Looper.getMainLooper())
    private var currentSpeed=START_SPEED
    fun start(){
        pentixRunnable.run()
    }

    fun stop(){
        handler.removeCallbacks(pentixRunnable)
    }

    private val pentixRunnable=object :Runnable{
        override fun run() {
            view.fieldMove(Pentix.Motions.DOWN)
            handler.postDelayed(this,currentSpeed)
        }

    }


    fun increaseSpeed(){
        currentSpeed= (currentSpeed*0.9).toLong()
    }

    companion object{
        private const val START_SPEED=1000L
    }
}