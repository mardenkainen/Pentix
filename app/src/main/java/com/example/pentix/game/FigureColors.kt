package com.example.pentix.game

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.pentix.R

class FigureColors(private val context: Context) {
    fun getColor(figureType: FigureType): Int {
        val color: Int
        when(figureType){
            FigureType.I5->color= ContextCompat.getColor(context, R.color.I5_color)
            FigureType.F5->color= ContextCompat.getColor(context,R.color.F5_color)
            FigureType.L5->color= ContextCompat.getColor(context,R.color.L5_color)
            FigureType.N5->color= ContextCompat.getColor(context,R.color.N5_color)
            FigureType.P5->color= ContextCompat.getColor(context,R.color.P5_color)
            FigureType.T5->color= ContextCompat.getColor(context,R.color.T5_color)
            FigureType.U5->color= ContextCompat.getColor(context,R.color.U5_color)
            FigureType.V5->color= ContextCompat.getColor(context,R.color.V5_color)
            FigureType.W5->color= ContextCompat.getColor(context,R.color.W5_color)
            FigureType.X5->color= ContextCompat.getColor(context,R.color.X5_color)
            FigureType.Y5->color= ContextCompat.getColor(context,R.color.Y5_color)
            FigureType.Z5->color= ContextCompat.getColor(context,R.color.Z5_color)
            FigureType.L4->color= ContextCompat.getColor(context,R.color.L4_color)
            FigureType.I4->color= ContextCompat.getColor(context,R.color.I4_color)
            FigureType.O4->color= ContextCompat.getColor(context,R.color.O4_color)
            FigureType.T4->color= ContextCompat.getColor(context,R.color.T4_color)
            FigureType.S4->color= ContextCompat.getColor(context,R.color.S4_color)
            FigureType.O1->color= ContextCompat.getColor(context,R.color.O1_color)
            FigureType.I2->color= ContextCompat.getColor(context,R.color.I2_color)
            FigureType.I3->color= ContextCompat.getColor(context,R.color.I3_color)
            FigureType.L3->color= ContextCompat.getColor(context,R.color.L3_color)
            else -> color=0
        }
        return color
    }

}