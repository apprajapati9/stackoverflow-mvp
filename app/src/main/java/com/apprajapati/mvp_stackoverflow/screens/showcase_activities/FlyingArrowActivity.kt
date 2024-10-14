package com.apprajapati.mvp_stackoverflow.screens.showcase_activities

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.view.View

internal class FlyingArrowView( context: Context ) : View( context )
{
    init
    {
        setBackgroundColor(-0x20) // Light Yellow
    }

    override fun onDraw(canvas: Canvas)
    {
        val filling_paint = Paint()
        filling_paint.style = Paint.Style.FILL
        filling_paint.color = Color.MAGENTA

        val outline_paint = Paint()
        outline_paint.style = Paint.Style.STROKE
        // Default color for a Paint is black.

        //  The arrow coordinates are selected so that Point (0, 0)
        //  is at the tip of the arrow, and the arrow points upwards.

        val arrow_shape_coordinates_x = intArrayOf(0, 15, 5, 5, 15, 0, -15, -5, -5, -15)

        val arrow_shape_coordinates_y = intArrayOf(0, 40, 30, 120, 160, 130, 160, 120, 30, 40)

        val flying_arrow = Path()

        flying_arrow.moveTo( arrow_shape_coordinates_x[0].toFloat(),
            arrow_shape_coordinates_y[0].toFloat())

        for (point_index in 1 until arrow_shape_coordinates_x.size)
        {
            flying_arrow.lineTo( arrow_shape_coordinates_x[point_index].toFloat(),
                arrow_shape_coordinates_y[point_index].toFloat())
        }

        flying_arrow.close()

        canvas.translate(150f, 250f)           // arrow tip to point (150, 250 )
        canvas.drawPath(flying_arrow, filling_paint)   // draw solid arrow

        canvas.rotate(45f)                     // 45 degrees clockwise
        canvas.drawPath(flying_arrow, outline_paint)   // draw a hollow arrow
        canvas.translate(0f, -200f)            // flying "up" 200 points
        canvas.drawPath(flying_arrow, filling_paint)

        // If you are running this program on an Android device that has
        // a wide screen, try to de-comment the lines below that are
        // commented out.

        canvas.rotate(45f)                     // 45 degrees clockwise
        //canvas.translate( 0, -200 ) ;   // flying "up" (i.e. to the right)
        canvas.drawPath(flying_arrow, filling_paint)

        //canvas.translate( 0, -100 ) ;   // flying "up" 100 points
        canvas.rotate(90f)                     // 90 degrees clockwise
        canvas.scale(1.5f, 1.5f)                // magnify everything by 1.5
        canvas.drawPath(flying_arrow, outline_paint)    // draw a hollow arrow
        canvas.translate(0f, -200f)             // flying "up" (i.e. down) 300 points
        canvas.drawPath(flying_arrow, filling_paint)

    }
}


class FlyingArrowActivity : Activity()
{
    public override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        setContentView( FlyingArrowView(this) )
    }
}
