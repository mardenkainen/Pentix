package com.example.pentix.game

import android.graphics.Point
import kotlin.math.abs

class Figure(figureType: FigureType) {
    private var type: FigureType = figureType
    private var figure:Array<Array<Byte>>
    private var coordinates: Point
    private var paddings:Array<Array<Int>>

    init {
        this.figure= FigureGenerator().generateFigure(figureType)
        paddings= arrayOf(arrayOf(0,0), arrayOf(0,0))
        coordinates= Point(0,0)
        initPaddings()
    }

    private fun initPaddings(){
        val free= abs(figure.size-figure[0].size)
        val firstOffset=free/2
        val secondOffset=free-firstOffset
        if (figure.size<=figure[0].size){
            paddings[0][0]=secondOffset
            paddings[1][1]=firstOffset
        }
        else{
            paddings[0][1]=secondOffset
            paddings[1][0]=firstOffset
        }
    }

    fun rotate(isClockwise:Boolean){
        figure=rotateMatrix(figure,isClockwise)
        paddings=rotateMatrix(paddings,isClockwise)
    }

    private inline fun <reified T> rotateMatrix(array: Array<Array<T>>, isClockwise:Boolean): Array<Array<T>> {
        val rotatedArray=Array(array[0].size){Array(array.size){array[0][0]} }
        for (i in 0 until array[0].size) {
            for (j in array.indices) {
                if (isClockwise){
                    rotatedArray[i][j] = array[array.size - j-1][i]
                }
                else{
                    rotatedArray[i][j] = array[j][array[0].size - i-1]
                }
            }
        }
        return rotatedArray
    }

    fun getType(): FigureType {
        return type
    }

    fun getFigure(): Array<Array<Byte>> {
        return figure
    }

    fun mirror(){
        var currByte:Byte
        for (i in figure.indices) {
            for (j in 0 until figure[i].size/2) {
                currByte=figure[i][j]
                figure[i][j] = figure[i][figure[0].size-j-1]
                figure[i][figure[0].size-j-1]=currByte
            }
        }
        val currInt:Int = paddings[0][1]
        paddings[0][1]=paddings[1][0]
        paddings[1][0]=currInt
    }

    fun getCellType(row:Int,col:Int): FigureType {
        return if (figure[row][col]==0.toByte()){
            FigureType.EMPTY
        } else{
            type
        }
    }

    fun incrementX(){
        coordinates.x++
    }
    fun incrementY(){
        coordinates.y++
    }
    fun decrementX(){
        coordinates.x--
    }
    fun decrementY(){
        coordinates.y--
    }
    fun getYOffset(): Int {
        return coordinates.y+paddings[0][0]
    }
    fun getXOffset(): Int {
        return coordinates.x+paddings[1][0]
    }
    fun setXOffset(value:Int){
        coordinates.x=value+paddings[1][0]
    }
}