package com.apprajapati.mvp_stackoverflow.screens.showcase_activities.moving_ball


import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.view.ContextMenu.ContextMenuInfo
import android.widget.Button
import com.apprajapati.mvp_stackoverflow.R

// Class Button, etc.
class MovingBallActivity : Activity()
{
    private var movingBallView: MovingBallView? = null

    public override fun onCreate( savedInstanceState: Bundle? )
    {
        super.onCreate( savedInstanceState )

        // The following call specifies that activity_moving_ball.xml is the file
        // that defines the UI of this Activity.

        setContentView( R.layout.activity_moving_ball )
        movingBallView = findViewById<View>(R.id.moving_ball_view) as MovingBallView

        // With the following call we get a reference to the Button
        // object that has been created based on the definition in main.xml.

        val colorButton = findViewById<View>( R.id.color_button ) as Button

        // with the following line we specify that the 'floating'
        // context menu must be shown when the 'color' button is long-pressed.

        registerForContextMenu( colorButton )
    }

    // The following four methods react to the pressings of the
    // buttons with which the ball can be moved.
    // Note that we do not need references to the Button objects in
    // this program as activity_moving_ball.xml specifies that the following
    // methods are called when the corresponding buttons are pressed.


    fun leftButtonClicked()
    {
        movingBallView!!.moveBallLeft()
    }

    fun downButtonClicked()
    {
        movingBallView!!.moveBallDown()
    }

    fun upButtonClicked()
    {
        movingBallView!!.moveBallUp()
    }

    fun rightButtonClicked()
    {
        movingBallView!!.moveBallRight()
    }

    // The following method is called when the Options
    // menu needs to be created. The definitions in
    // res/menu/color_selection_menu.xml are used to
    // create the menu.

    override fun onCreateOptionsMenu( menu: Menu ) : Boolean
    {
        super.onCreateOptionsMenu( menu )
        val inflater = menuInflater
        inflater.inflate( R.menu.color_selection_menu, menu )
        return true
    }

    // In this program we have two menus which are specified with
    // the same XML file.
    // The following method is called when the 'floating'
    // context menu needs to be created.


    override fun onCreateContextMenu( menu: ContextMenu, v: View,
                                      menuInfo: ContextMenuInfo? )
    {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.color_selection_menu, menu)
    }

    //  The following method is used to process selections from
    //  both menus, the options menu and the context menu.

    private  fun processMenuSelection(menuItemId: Int ): Boolean
    {
        return when ( menuItemId )
        {
            R.id.red_color_item ->
            {
                movingBallView!!.setBallColor( Color.RED )
                true
            }
            R.id.white_color_item ->
            {
                movingBallView!!.setBallColor( Color.WHITE )
                true
            }
            R.id.yellow_color_item ->
            {
                movingBallView!!.setBallColor( Color.YELLOW )
                true
            }
            R.id.green_color_item ->
            {
                movingBallView!!.setBallColor( Color.GREEN )
                true
            }
            R.id.blue_color_item ->
            {
                movingBallView!!.setBallColor( Color.BLUE )
                true
            }
            R.id.magenta_color_item ->
            {
                movingBallView!!.setBallColor( Color.MAGENTA )
                true
            }
            R.id.cyan_color_item ->
            {
                movingBallView!!.setBallColor( Color.CYAN )
                true
            }
            R.id.gray_color_item ->
            {
                movingBallView!!.setBallColor( Color.GRAY )
                true
            }
            R.id.light_gray_color_item ->
            {
                movingBallView!!.setBallColor( Color.LTGRAY )
                true
            }
            else -> false
        }
    }

    // The following method is called when a selection in
    // the Options menu has been made.

    override fun onOptionsItemSelected(menuItem: MenuItem ) : Boolean
    {
        return if ( processMenuSelection( menuItem.itemId ) )
        {
            true
        } else
        {
            super.onOptionsItemSelected( menuItem )
        }
    }

    // The following method is called when a selection in
    // the context menu has been made.

    override fun onContextItemSelected(menuItem: MenuItem ) : Boolean
    {
        return if ( processMenuSelection( menuItem.itemId ) )
        {
            true
        } else
        {
            super.onContextItemSelected(menuItem)
        }
    }
}