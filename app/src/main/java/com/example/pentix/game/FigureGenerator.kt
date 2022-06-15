package com.example.pentix.game

class FigureGenerator {
    private val types=enumValues<FigureType>()
    fun generateFigure(figureType: FigureType): Array<Array<Byte>> {
        val figure:Array<Array<Byte>>
        when(figureType){
            FigureType.I5 ->figure=arrayOf(arrayOf(1,1,1,1,1))
            FigureType.F5 ->figure=arrayOf(arrayOf(0,1,1),arrayOf(1,1,0),arrayOf(0,1,0))
            FigureType.L5 ->figure=arrayOf(arrayOf(0,0,0,1),arrayOf(1,1,1,1))
            FigureType.N5 ->figure=arrayOf(arrayOf(1,1,0,0),arrayOf(0,1,1,1))
            FigureType.P5 ->figure=arrayOf(arrayOf(1,1),arrayOf(1,1),arrayOf(1,0))
            FigureType.T5 ->figure=arrayOf(arrayOf(1,1,1),arrayOf(0,1,0),arrayOf(0,1,0))
            FigureType.U5 ->figure=arrayOf(arrayOf(1,0,1),arrayOf(1,1,1))
            FigureType.V5 ->figure=arrayOf(arrayOf(1,0,0),arrayOf(1,0,0),arrayOf(1,1,1))
            FigureType.W5 ->figure=arrayOf(arrayOf(1,0,0),arrayOf(1,1,0),arrayOf(0,1,1))
            FigureType.X5 ->figure=arrayOf(arrayOf(0,1,0),arrayOf(1,1,1),arrayOf(0,1,0))
            FigureType.Y5 ->figure=arrayOf(arrayOf(0,0,1,0),arrayOf(1,1,1,1))
            FigureType.Z5 ->figure=arrayOf(arrayOf(0,0,1),arrayOf(1,1,1),arrayOf(1,0,0))
            FigureType.L4 ->figure=arrayOf(arrayOf(0,0,1), arrayOf(1,1,1))
            FigureType.I4 ->figure=arrayOf(arrayOf(1,1,1,1))
            FigureType.O4 ->figure=arrayOf(arrayOf(1,1), arrayOf(1,1))
            FigureType.T4 ->figure=arrayOf(arrayOf(1,1,1), arrayOf(0,1,0))
            FigureType.S4 ->figure=arrayOf(arrayOf(0,1,1), arrayOf(1,1,0))
            FigureType.O1 ->figure=arrayOf(arrayOf(1))
            FigureType.I2 ->figure=arrayOf(arrayOf(1,1))
            FigureType.I3 ->figure=arrayOf(arrayOf(1,1,1))
            FigureType.L3 ->figure=arrayOf(arrayOf(0,1), arrayOf(1,1))
            else -> {figure= arrayOf(arrayOf(0))}
        }
        return figure
    }

    fun getRandomFigureType(): FigureType {
        return types[(1 until types.size).random()]
    }
}