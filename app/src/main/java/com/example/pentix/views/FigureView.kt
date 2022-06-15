package com.example.pentix.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.pentix.R
import com.example.pentix.game.Figure
import com.example.pentix.game.FigureColors
import com.example.pentix.game.FigureType
import kotlin.math.min

class FigureView(context: Context
                 , attributesSet: AttributeSet?,
                 defStyleAttr:Int,
                 defStyleRes:Int):
    View(context,attributesSet,defStyleAttr,defStyleRes) {
    constructor(context: Context,attributesSet: AttributeSet?,defStyleAttr: Int) : this(context,attributesSet,defStyleAttr,0)
    constructor(context: Context,attributesSet: AttributeSet?) : this(context,attributesSet,0)
    constructor(context: Context) : this(context,null)


    private var figure:Figure=Figure(FigureType.EMPTY)
    private var figurePaint= Paint(Paint.ANTI_ALIAS_FLAG)
    private var textPaint= Paint(Paint.ANTI_ALIAS_FLAG)
    private var cellSize=0
    private var text=""
    private var font=0
    private var headHeight=0
    private var textHeight=0
    private var textBounds=Rect()


    init {
        initAttributes(attributesSet, defStyleAttr, defStyleRes)
        figurePaint.style=Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas?) {
        canvas!!.drawColor(Color.DKGRAY)
        drawHead(canvas)
        drawText(canvas)
        drawFigure(canvas)
        super.onDraw(canvas)
    }

    private fun drawHead(canvas: Canvas?){
        figurePaint.color=Color.WHITE
        val rect= RectF(0f,0f,width.toFloat(),headHeight.toFloat())
        canvas!!.drawRoundRect(rect,4F,4F,figurePaint)
    }

    private fun drawText(canvas: Canvas?){
        val textWidth = textPaint.measureText(text)
        textHeight = textBounds.height()

        canvas!!.drawText(text,
            width/2 - (textWidth / 2f),
            headHeight/2 + (textHeight /2f),
            textPaint
        )
    }

    private fun drawFigure(canvas: Canvas?){
        val marginLeft=(canvas!!.width-cellSize*figure.getFigure()[0].size)/2
        val marginTop=(canvas.height-headHeight-cellSize*figure.getFigure().size)/2
        canvas.translate(marginLeft.toFloat(),(marginTop+headHeight).toFloat())
        for (i in 0 until figure.getFigure().size){
            for (j in 0 until figure.getFigure()[0].size){
                drawBlock(canvas,i,j,figure.getCellType(i,j))
            }
        }
    }

    private fun drawBlock(canvas: Canvas?,row:Int,col:Int,type: FigureType){
        if (type== FigureType.EMPTY) return
        figurePaint.color= FigureColors(context).getColor(type)
        val top=(row*cellSize).toFloat()
        val bottom=((row+1)*cellSize).toFloat()
        val left=(col*cellSize).toFloat()
        val right=((col+1)*cellSize).toFloat()
        val rect=RectF(left, top, right, bottom)
        canvas!!.drawRect(rect,figurePaint)
    }

    fun setFigure(figure: Figure){
        this.figure=figure
        invalidate()
    }

    fun setFigure(figureType: FigureType){
        figure= Figure(figureType)
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width= min(this.measuredWidth-2* PADDING,this.measuredHeight-2* PADDING)
        cellSize= width/5
        setMeasuredDimension(cellSize,
            cellSize*5+headHeight
        )
    }

    private fun initAttributes(attributesSet: AttributeSet?,defStyleAttr: Int,defStyleRes: Int){
        val typedArray=context.obtainStyledAttributes(attributesSet, R.styleable.FigureView,defStyleAttr,defStyleRes)
        text=typedArray.getString(R.styleable.FigureView_headText)!!.uppercase()
        font=typedArray.getResourceId(R.styleable.FigureView_fontFamily,0)
        textPaint=Paint(Paint.ANTI_ALIAS_FLAG)
        textPaint.color=typedArray.getColor(R.styleable.FigureView_headTextColor,Color.BLACK)
        if (font>0){
            textPaint.typeface = ResourcesCompat.getFont(context,font)
        }
        textPaint.textSize= typedArray.getDimension(R.styleable.FigureView_android_textSize,10f)
        textPaint.getTextBounds(text,0,text.length,textBounds)
        textHeight=textBounds.height()
        headHeight= textHeight*3
        typedArray.recycle()
    }


    companion object{
        private const val PADDING=5
    }
}