package com.apprajapati.mvp_stackoverflow.screens.showcase_activities.moving_ball

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class MovingBallView(context: Context?, attributes: AttributeSet?) : View(context, attributes)
{
    private var ballCenterPointX = 100
    private var ballCenterPointY = 100
    private var ballColor = Color.RED

    private val addition = 10

    // Default color for a Paint is black.
    private val fillingPaint = Paint().apply {
        style = Paint.Style.FILL
        color = ballColor
    }

    private val outlinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    //  The following constructor is needed when MovingBallView object is
    //  specified in an XML file, and is thus created automatically.

//    constructor( context: Context? ) : super( context, attributes )
//    {
//        setBackgroundColor( 0xFFF5F5F5.toInt() ) // white smoke color
//    }

    public override fun onSizeChanged(currentWidthOfThisView: Int,
                                      currentHeightOfThisView: Int,
                                      oldWidthOfThisView: Int,
                                      oldHeightOfThisView: Int)
    {
        ballCenterPointX = currentWidthOfThisView / 2
        ballCenterPointY = currentHeightOfThisView / 2
    }

    fun moveBallLeft()
    {
        ballCenterPointX -= addition
        invalidate()
    }

    fun moveBallDown()
    {
        ballCenterPointY += addition
        invalidate()
    }

    fun moveBallUp()
    {
        ballCenterPointY -= addition
        invalidate()
    }

    fun moveBallRight()
    {
        ballCenterPointX += addition
        invalidate()
    }

    fun setBallColor(newColor: Int )
    {
        ballColor = newColor
        invalidate()
    }

    override fun onDraw( canvas: Canvas )
    {


        canvas.drawCircle(ballCenterPointX.toFloat(),
            ballCenterPointY.toFloat(), 64f, fillingPaint)


        canvas.drawCircle( ballCenterPointX.toFloat(),
            ballCenterPointY.toFloat(), 64f, outlinePaint )

        canvas.drawText( "(" + ballCenterPointX +
                ", " + ballCenterPointY + ")", 20f, 20f, outlinePaint )
    }
}