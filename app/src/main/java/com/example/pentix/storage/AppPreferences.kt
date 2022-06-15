package com.example.pentix.storage

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context) {
    private var data: SharedPreferences=context.getSharedPreferences("APP_PREFERENCES",Context.MODE_PRIVATE)

    fun  saveScore(highScore:Int){
        data.edit().putInt("HIGH_SCORE",highScore).apply()
    }

    fun  getHighScore():Int{
        return data.getInt("HIGH_SCORE",0)
    }

    fun clearHighScore(){
        data.edit().putInt("HIGH_SCORE",0).apply()
    }
}