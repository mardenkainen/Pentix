package com.example.pentix.views

import com.example.pentix.game.FigureType
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.pentix.game.Pentix
import com.example.pentix.FieldConstants
import com.example.pentix.game.FigureColors
import kotlin.math.abs
import kotlin.math.min


class FieldView(context: Context
                , attributesSet: AttributeSet?,
                defStyleAttr:Int,
                defStyleRes:Int):
    View(context,attributesSet,defStyleAttr,defStyleRes) {
    constructor(context: Context,attributesSet: AttributeSet?,defStyleAttr: Int) : this(context,attributesSet,defStyleAttr,0)
    constructor(context: Context,attributesSet: AttributeSet?) : this(context,attributesSet,0)
    constructor(context: Context) : this(context,null)

    private var paint=Paint(Paint.ANTI_ALIAS_FLAG)
    private var pentix: Pentix =
        Pentix(FieldConstants.ROW_COUNT.value, FieldConstants.COLUMN_COUNT.value)
    private var cellSize=0
    private var handler= ViewHandler(this)

    init {
        paint.style=Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas?) {
        drawBackground(canvas)
        drawField(canvas)
        drawFigure(canvas)
        super.onDraw(canvas)
    }

    private fun drawBackground(canvas: Canvas?){
        canvas?.drawColor(Color.DKGRAY)
    }

    private fun drawField(canvas: Canvas?){
        for (i in 0 until FieldConstants.ROW_COUNT.value){
            for (j in 0 until FieldConstants.COLUMN_COUNT.value){
                drawBlock(canvas,i,j,pentix.getCellType(i,j))
            }
        }
    }

    private fun drawFigure(canvas: Canvas?){
        val figure=pentix.getFigure()

        for (i in 0 until figure.getFigure().size){
            for (j in 0 until figure.getFigure()[0].size){
                drawBlock(canvas,i+figure.getYOffset(),j+figure.getXOffset(),figure.getCellType(i,j))
            }
        }
    }

    private fun drawBlock(canvas: Canvas?,row:Int,col:Int,type: FigureType){
        if (type== FigureType.EMPTY) return
        paint.color=FigureColors(context).getColor(type)
        val top=(row*cellSize+ PADDING + BLOCK_MARGIN).toFloat()
        val bottom=((row+1)*cellSize+ PADDING - BLOCK_MARGIN).toFloat()
        val left=(col*cellSize+ PADDING + BLOCK_MARGIN).toFloat()
        val right=((col+1)*cellSize+ PADDING - BLOCK_MARGIN).toFloat()
        val rect=RectF(left, top, right, bottom)
        canvas!!.drawRoundRect(rect,6F,6F,paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        cellSize= min((measuredWidth - 2*PADDING) / FieldConstants.COLUMN_COUNT.value,(measuredHeight - 2*PADDING) / FieldConstants.ROW_COUNT.value)
        setMeasuredDimension(cellSize * FieldConstants.COLUMN_COUNT.value + 2*PADDING,
            cellSize * FieldConstants.ROW_COUNT.value + 2*PADDING)
    }

    fun increaseGameSpeed(){
        handler.increaseSpeed()
    }

    fun saveFigure(): FigureType {
        val savedFigure= pentix.saveFigure()
        invalidate()
        return savedFigure
    }

    fun start(){
        if (!pentix.isGameOver() && !pentix.isGameActive()){
            pentix.setGameStatus(Pentix.Statuses.ACTIVE)
            handler.start()
        }
    }

    fun finish(){
        pentix.setGameStatus(Pentix.Statuses.OVER)
        handler.stop()
    }

    fun pause(){
        if (pentix.isGameActive()){
            pentix.setGameStatus(Pentix.Statuses.PAUSE)
            handler.stop()
        }
    }

    fun fieldMove(motion: Pentix.Motions){
        pentix.performMotion(motion)
        invalidate()
    }

    private var startX=0f
    private var startY=0f
    private var move=0

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val currentX: Float
        val currentY: Float
        when(event!!.action){
            MotionEvent.ACTION_DOWN ->{
                startX= event.x
                startY= event.y
                move=0
            }
            MotionEvent.ACTION_MOVE ->{
                currentX=event.x
                currentY=event.y
                val dx=currentX-startX
                val dy=currentY-startY
                if (abs(dx) >=cellSize){
                    move+=1
                    if (dx<0){
                        fieldMove(Pentix.Motions.LEFT)
                    }
                    if (dx>0){
                        fieldMove(Pentix.Motions.RIGHT)
                    }
                    startX=currentX
                }
                if (abs(dy) >=cellSize){
                    move+=1
                    if (dy>0){
                        fieldMove(Pentix.Motions.DOWN)
                    }
                    invalidate()
                    startY=currentY
                }
            }
            MotionEvent.ACTION_UP ->{
                if (move==0){
                    fieldMove(Pentix.Motions.ROTATE)
                    invalidate()
                }
                move=0
            }
        }
        invalidate()
        return true

    }

    fun setGameListener(listener: Pentix.GameActionListener){
        pentix.setGameActionListener(listener)
    }

    companion object{
        private const val PADDING=4
        private const val BLOCK_MARGIN=3
    }

}