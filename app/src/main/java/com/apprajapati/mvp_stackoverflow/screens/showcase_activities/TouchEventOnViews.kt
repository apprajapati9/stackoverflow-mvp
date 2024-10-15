package com.apprajapati.mvp_stackoverflow.screens.showcase_activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import kotlin.math.pow
import kotlin.math.sqrt

// Float values are used to describe Ball position on the screen.
// In the corresponding .java program int values are in use.
// Also the balls are slightly bigger here than in the .java version.

internal class Ball(private val position: PointF,
                    private var ballColor: Int )
{

    private val fillingPaint = Paint().apply {
        style = Paint.Style.FILL
        color = ballColor
    }

    private val outlinePaint = Paint().apply {
        style = Paint.Style.STROKE
    }

    private var ballDiameter = 152F

    private var thisBallIsActivated = false

    fun activateBall()
    {
        thisBallIsActivated = true
    }

    fun deactivateBall()
    {
        thisBallIsActivated = false
    }

    fun getBallCenterPointX() : Float
    {
        return position.x
    }

    fun getBallCenterPointY() : Float
    {
        return position.y
    }

    fun getBallDiameter() : Float
    {
        return ballDiameter
    }

    fun moveRight()
    {
        position.x += 3
    }

    fun moveLeft()
    {
        position.x -= 3
    }

    fun moveUp()
    {
        position.y -= 3
    }

    fun moveDown()
    {
        position.y += 3
    }

    fun moveThisBall(movementInDirectionX: Float,
                     movementInDirectionY: Float )
    {
        position.x += movementInDirectionX
        position.y += movementInDirectionY
    }

    fun moveToPosition( newPointX: Float,
                          newPointY: Float )
    {
        position.x = newPointX
        position.y = newPointY
    }

    fun shrink()
    {
        //  The if-construct ensures that the ball does not become
        //  too small.

        if ( ballDiameter > 10 )
        {
            ballDiameter -= 6
        }
    }

    fun enlarge()
    {
        ballDiameter += 6
    }

    fun setDiameter( newDiameter: Float )
    {
        if ( newDiameter > 5 )
        {
            ballDiameter = newDiameter
        }
    }

    fun setColor(newColor: Int )
    {
        ballColor = newColor
    }

    fun containsPoint(givenPoint: PointF) : Boolean
    {
        val ballRadius = ballDiameter / 2

        //  Here we use the Pythagorean theorem to calculate the distance
        //  from the given point to the center point of the ball.
        //  See the note at the end of this file.

        val distanceFromGivenPointToBallCenter = sqrt(
            (position.x - givenPoint.x.toDouble()).pow(2.0) +
                    (position.y - givenPoint.y.toDouble()).pow(2.0)
        ).toFloat()

        return distanceFromGivenPointToBallCenter <= ballRadius
    }

    fun draw( canvas: Canvas )
    {
        canvas.drawCircle( position.x, position.y,
            ballDiameter / 2, fillingPaint )

        //  If this ball is activated, it will have a thick black edge

        if (thisBallIsActivated)
        {
            outlinePaint.strokeWidth = 3F
        }

        canvas.drawCircle( position.x, position.y,
            ballDiameter / 2, outlinePaint )
    }
}

internal class MovingBallsWithPointerView( context: Context? ) : View( context )
{
    private var viewWidth = 0F
    private var viewHeight = 0F

    private var firstBall: Ball? = null
    private var secondBall: Ball? = null
    private var thirdBall: Ball? = null

    private var ballBeingMoved: Ball? = null

    private var previousPointerPosition: PointF? = null

    init
    {
        setBackgroundColor( 0xFFF5F5F5.toInt() ) // white smoke color
    }

    public override fun onSizeChanged( width: Int,
                                       height: Int,
                                       oldW: Int,
                                       oldH: Int )
    {
        viewWidth = width.toFloat()
        viewHeight = height.toFloat()

        // We create new balls always when the size of this view
        // is changed. This seems to happen before the balls are
        // drawn for the first time.

        firstBall = Ball( PointF(viewWidth/2, viewHeight/4 ),
            Color.RED )

        secondBall = Ball( PointF(viewWidth / 2,
            viewHeight / 2),
            Color.GREEN)

        thirdBall = Ball( PointF(viewWidth / 2,
            viewHeight * 3 / 4),
            Color.BLUE )
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(motionEvent: MotionEvent ) : Boolean
    {
        if ( motionEvent.action == MotionEvent.ACTION_DOWN )
        {
            val pointerPosition = PointF( motionEvent.x,
                motionEvent.y )

            if ( firstBall!!.containsPoint( pointerPosition ) )
            {
                ballBeingMoved = firstBall
                ballBeingMoved!!.activateBall()
            }
            else if ( secondBall!!.containsPoint( pointerPosition ) )
            {
                ballBeingMoved = secondBall
                ballBeingMoved!!.activateBall()
            }
            else if ( thirdBall!!.containsPoint( pointerPosition ) )
            {
                ballBeingMoved = thirdBall
                ballBeingMoved!!.activateBall()
            }

            previousPointerPosition = pointerPosition
        }
        else if ( motionEvent.action == MotionEvent.ACTION_MOVE )
        {
            if ( ballBeingMoved != null )
            {
                val newPointerPosition = PointF( motionEvent.x,
                    motionEvent.y )

                val pointerMovementX = ( newPointerPosition.x
                        - previousPointerPosition!!.x )
                val pointerMovementY = ( newPointerPosition.y
                        - previousPointerPosition!!.y )

                previousPointerPosition = newPointerPosition

                ballBeingMoved!!.moveThisBall( pointerMovementX,
                    pointerMovementY )
            }
        }
        else if ( motionEvent.action == MotionEvent.ACTION_UP )
        {
            if ( ballBeingMoved != null )
            {
                ballBeingMoved!!.deactivateBall()
                ballBeingMoved = null
            }
        }

        invalidate()

        return true
    }

    override fun onDraw( canvas: Canvas )
    {
        firstBall!!.draw( canvas )
        secondBall!!.draw( canvas )
        thirdBall!!.draw( canvas )
    }
}

class MovingBallsWithPointerActivity : Activity()
{
    public override fun onCreate( savedInstanceState: Bundle? )
    {
        super.onCreate( savedInstanceState )

        setContentView( MovingBallsWithPointerView( this ) )
    }
}