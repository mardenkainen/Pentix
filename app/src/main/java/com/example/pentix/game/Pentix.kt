package com.example.pentix.game

import com.example.pentix.FieldConstants

class Pentix(rows:Int, columns:Int) {

    private var field=Array(rows){Array(columns){ FigureType.EMPTY} }
    private var state= Statuses.AWAITING_START
    private var savedFigure: FigureType=FigureType.EMPTY
    private var currentFigure: Figure = Figure(FigureType.Z5)
    private var nextFigure: FigureType = FigureType.EMPTY
    private var gameListener:GameActionListener?=null
    private var isSavedInThisStep=false

    fun getFigure(): Figure {
        return currentFigure
    }

    fun getCellType(row: Int,col:Int): FigureType {
        return field[row][col]
    }

    fun setCellType(type: FigureType, row:Int, col: Int){
        field[row][col]=type
    }

    fun isGameOver(): Boolean {
        return state== Statuses.OVER
    }

    fun isGameActive(): Boolean {
        return state== Statuses.ACTIVE
    }

    fun isGamePause():Boolean{
        return state==Statuses.PAUSE
    }

    fun isGameAwaitingStart(): Boolean {
        return state== Statuses.AWAITING_START
    }

    fun setGameStatus(newStatuses: Statuses){
        if (!isGameOver()){
            if (newStatuses==Statuses.ACTIVE && !isGamePause()){
                nextFigure=FigureGenerator().getRandomFigureType()
                updateCurrentFigure()
            }
            state=newStatuses
        }
    }

    fun performMotion(motion: Motions){
        if (!isGameActive()){
            return
        }
        when(motion){
            Motions.LEFT ->currentFigure.decrementX()
            Motions.RIGHT ->currentFigure.incrementX()
            Motions.DOWN ->currentFigure.incrementY()
            Motions.ROTATE ->currentFigure.rotate(true)
            Motions.MIRROR ->currentFigure.mirror()
        }
        if (!isCurrentFigureInValidPosition()){
            when(motion){
                Motions.LEFT ->currentFigure.incrementX()
                Motions.RIGHT ->currentFigure.decrementX()
                Motions.DOWN ->{
                    currentFigure.decrementY()
                    attachFigureToField()
                    deleteFilledRows()
                }
                Motions.ROTATE ->currentFigure.rotate(false)
                Motions.MIRROR ->currentFigure.mirror()
            }
        }
    }

    private fun attachFigureToField(){
        val figure=currentFigure.getFigure()
        val xOffset=currentFigure.getXOffset()
        val yOffset=currentFigure.getYOffset()
        for (i in figure.indices){
            for (j in 0 until figure[0].size){
                if (figure[i][j]!=0.toByte()){
                    field[yOffset+i][xOffset+j]=currentFigure.getType()
                }
            }
        }
        updateCurrentFigure()
    }

    private fun checkSpaceForNewFigure(){
        if (!isCurrentFigureInValidPosition()){
            state= Statuses.OVER
            gameListener!!.gameOver()
        }
    }

    private fun deleteFilledRows(): Int {
        var emptyRows=0
        for (i in field.indices){
            if (isRowFilled(i)){
                shiftRows(i)
                emptyRows++
            }
        }
        gameListener!!.onRowsFilled(emptyRows)
        return emptyRows
    }

    private fun isRowFilled(row: Int): Boolean {
        for (i in 0 until field[0].size){
            if (field[row][i]== FigureType.EMPTY){
                return false
            }
        }
        return true
    }

    private fun shiftRows(row: Int){
        for (i in row downTo 1){
            field[i]=field[i-1]
        }
        field[0]=Array(FieldConstants.COLUMN_COUNT.value){ FigureType.EMPTY}
    }

    private fun isCurrentFigureInValidPosition(): Boolean {
        val figure=currentFigure.getFigure()
        val xOffset=currentFigure.getXOffset()
        val yOffset=currentFigure.getYOffset()
        if (yOffset <0 || xOffset<0){
            return false
        }
        if (yOffset+figure.size > FieldConstants.ROW_COUNT.value){
            return false
        }
        if (xOffset+figure[0].size > FieldConstants.COLUMN_COUNT.value){
            return false
        }

        for (i in figure.indices){
            for (j in 0 until figure[0].size){
                if (field[yOffset+i][xOffset+j]!= FigureType.EMPTY && figure[i][j]!=0.toByte()){
                    return false
                }
            }
        }
        return true
    }

    private fun updateCurrentFigure(){
        currentFigure= Figure(nextFigure)
        currentFigure.setXOffset(calculateFigureXOffset(currentFigure))
        nextFigure=FigureGenerator().getRandomFigureType()
        gameListener!!.onFigureTouchedGround(nextFigure)
        checkSpaceForNewFigure()
        isSavedInThisStep=false
    }

    private fun calculateFigureXOffset(figure: Figure): Int {
        return (FieldConstants.COLUMN_COUNT.value-figure.getFigure()[0].size+1)/2
    }

    fun saveFigure(): FigureType {
        if (isSavedInThisStep) return savedFigure
        if (savedFigure == FigureType.EMPTY){
            savedFigure=currentFigure.getType()
            updateCurrentFigure()
        }
        else{
            val temp=currentFigure.getType()
            currentFigure= Figure(savedFigure)
            currentFigure.setXOffset(calculateFigureXOffset(currentFigure))
            savedFigure=temp
        }
        isSavedInThisStep=true
        return savedFigure
    }

    enum class Statuses{
        AWAITING_START,ACTIVE,PAUSE,OVER
    }

    enum class Motions{
        LEFT,RIGHT,DOWN,ROTATE,MIRROR
    }

    fun setGameActionListener(listener: GameActionListener){
        gameListener=listener
    }

    interface GameActionListener{

        fun onFigureTouchedGround(nextFigureType: FigureType)

        fun onRowsFilled(numOfFilledRows:Int)

        fun gameOver()
    }
}