package com.apprajapati.mvp_stackoverflow.screens.showcase_activities

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.google.android.material.button.MaterialButton

class ButtonDemoConstraintLayout( context : Context)
    : ConstraintLayout( context )
{
    init
    {
        id = generateViewId()  // id for parent

        val basicLayoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT )

        //setLayoutParams( basic_layout_params )
        layoutParams = basicLayoutParams

        setBackgroundColor( Color.LTGRAY )

        val constraintSet = ConstraintSet()

        constraintSet.clone( this )

        val changingText = TextView( context )

        changingText.text = "HELLO!"
        changingText.textSize = 32F
        changingText.id = View.generateViewId()

        addView( changingText )

        constraintSet.constrainWidth( changingText.id,
            ConstraintSet.WRAP_CONTENT )
        constraintSet.constrainHeight( changingText.id,
            300 )

        constraintSet.centerHorizontally( changingText.id, ConstraintSet.PARENT_ID )

        val buttonToPress = MaterialButton( context )

        buttonToPress.id = generateViewId()

        buttonToPress.setBackgroundColor( 0xFF808000.toInt() ) // Olive color.
        buttonToPress.setTextColor( Color.WHITE )
        buttonToPress.textSize = 24F
        buttonToPress.text = "PRESS"
        buttonToPress.cornerRadius = 40  // Works only with MaterialButton, I guess.

        buttonToPress.setOnClickListener{

            if ( changingText.text == "HELLO!" )
            {
                changingText.text = "WELL DONE!"
            }
            else
            {
                changingText.text = "HELLO!"
            }
        }

        addView( buttonToPress )

        constraintSet.constrainWidth( buttonToPress.id,
            400 )
        constraintSet.constrainHeight( buttonToPress.id,
            300 )

        /*
        // The following two connections center the button horizontally.
        constraint_set.connect( button_to_press.id, ConstraintSet.LEFT,
           ConstraintSet.PARENT_ID, ConstraintSet.LEFT )
        constraint_set.connect( button_to_press.id, ConstraintSet.RIGHT,
           ConstraintSet.PARENT_ID, ConstraintSet.RIGHT )
        */

        // The following seems to be an easier way for horizontal centering.
        constraintSet.centerHorizontally( buttonToPress.id, ConstraintSet.PARENT_ID )

        // Instead of connecting, we'll do chaining.
        //constraint_set.connect( button_to_press.id, ConstraintSet.TOP,
        // changing_text.id, ConstraintSet.BOTTOM, 300 )

        val viewsToChain = intArrayOf( changingText.id, buttonToPress.id )

        constraintSet.createVerticalChain( ConstraintSet.PARENT_ID,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM,
            viewsToChain,
            null,
            ConstraintSet.CHAIN_PACKED )

        constraintSet.applyTo( this )
    }
}


class ButtonDemoNoXMLActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        val buttonDemoConstraintLayout = ButtonDemoConstraintLayout( this )
        setContentView( buttonDemoConstraintLayout )
    }
}