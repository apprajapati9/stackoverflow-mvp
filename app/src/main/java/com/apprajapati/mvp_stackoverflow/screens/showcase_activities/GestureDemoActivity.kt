package com.apprajapati.mvp_stackoverflow.screens.showcase_activities

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import java.util.*


internal class GesturesDemoView( context: Context? ) : View( context ), GestureDetector.OnGestureListener
{
    private var gestureDetector: GestureDetector = GestureDetector( context, this )

    private var textLinesToScreen = ArrayList<String>()

    private val textPaint = Paint().apply {
        style= Paint.Style.FILL
        textSize = 50F
    }

    init
    {

        textLinesToScreen.add( "With this Kotlin app, you can" )
        textLinesToScreen.add( "explore which gesture events" )
        textLinesToScreen.add( "take place when you play" )
        textLinesToScreen.add( "with the touch screen." )
        textLinesToScreen.add( "" )

        setBackgroundColor( 0xFFF5F5F5.toInt() ) // white smoke color
    }

    override fun onDown(motionEvent: MotionEvent ) : Boolean
    {
        textLinesToScreen.add( "onDown()" )
        invalidate()
        return true
    }

    override fun onFling(firstDownMotion: MotionEvent?,
                         lastMoveMotion: MotionEvent,
                         velocityX: Float, velocityY: Float ) : Boolean
    {
        textLinesToScreen.add( "onFling()" )
        invalidate()
        return true
    }

    override fun onLongPress(firstDownMotion: MotionEvent )
    {
        textLinesToScreen.add( "onLongPress()" )
        invalidate()
    }


    override fun onScroll(firstDownMotion: MotionEvent?,
                          lastMoveMotion: MotionEvent,
                          distanceX: Float, distanceY: Float ) : Boolean
    {
        textLinesToScreen.add( "onScroll()" )
        invalidate()
        return true
    }

    override fun onShowPress(downMotion: MotionEvent )
    {
        textLinesToScreen.add( "onShowPress()" )
        invalidate()
    }

    override fun onSingleTapUp(upMotion: MotionEvent ) : Boolean
    {
        textLinesToScreen.add("onSingleTapUp()")
        invalidate()
        return true
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }


    override fun onTouchEvent(motionEvent: MotionEvent ) : Boolean
    {
        gestureDetector.onTouchEvent( motionEvent )
        return true
    }

    override fun onDraw( canvas: Canvas )
    {


        // We'll print only 10 lines to the screen.
        // The older lines from the beginning of the list will
        // be deleted.

        while ( textLinesToScreen.size > 10 )
        {
            textLinesToScreen.removeAt( 0 )
        }

        for ( lineIndex in textLinesToScreen.indices )
        {
            val textLineToScreen = textLinesToScreen[ lineIndex ]

            if ( textLineToScreen == "onDown()" )
            {
                textPaint.color = Color.BLACK
            }
            else if ( textLineToScreen == "onFling()" )
            {
                textPaint.color = Color.BLUE
            }
            else if ( textLineToScreen == "onLongPress()" )
            {
                textPaint.color = Color.RED
            }
            else if ( textLineToScreen == "onScroll()" )
            {
                textPaint.color = Color.GRAY
            }
            else if ( textLineToScreen == "onShowPress()" )
            {
                textPaint.color = Color.MAGENTA
            }
            else if ( textLineToScreen == "onSingleTapUp()" )
            {
                textPaint.color = Color.CYAN
            }

            canvas.drawText( textLineToScreen,
                20F, 48 + 100 * lineIndex.toFloat(), textPaint )
        }
    }
}

class GesturesDemoActivity : Activity()
{
    // onCreate() will be called when the activity is first created.

    public override fun onCreate( savedInstanceState: Bundle? )
    {
        super.onCreate(savedInstanceState)
        setContentView(GesturesDemoView(this))
    }
}