package com.apprajapati.mvp_stackoverflow.screens.showcase_activities

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.apprajapati.mvp_stackoverflow.R
import kotlin.random.Random


// Classes Canvas, Color, Paint, RectF, etc.
// Classes View, Display, WindowManager, etc.


// Here a class named Sprite is defined. A Sprite is a sequence of
// pieces of an image. Those pieces, frames, will be drawn one by one when the
// draw() method is called. After all frames are drawn, the Sprite can
// be considered 'dead', and cannot be used any more.
class Sprite (
    private var spriteImage: Bitmap,
    private var numberOfFramesToShow: Int,
    private var spriteCenterPointX: Int,  // x-position on canvas
    private var spriteCenterPointY: Int,  // y-position on canvas
    private var numberOfFramesToDelay: Int
) {
    private var currentFrameIndex: Int = 0

    private var frameWidth: Int = spriteImage.width / numberOfFramesToShow
    private var frameHeight: Int = spriteImage.height

    private var spriteState: Int = DELAYING_BEFORE_SHOW

    // The second parameter for the constructor tells how many frames,
    // pieces of an image, can be found inside the image that is given
    // as the first parameter.
    // The image must be such that its frames are horizontally organized.
    // With the last parameter, you can specify how many frames will be
    // delayed before the showing of the sprite begins.
    init {
        // System.out.print( "\n  " + frame_width + " x " + frame_height ) ;
    }

    fun sprite_has_finished(): Boolean {
        return (spriteState == SPRITE_HAS_FINISHED)
    }

    fun draw(canvas: Canvas) {
        if (spriteState == SPRITE_IS_ALIVE) {
            //  You can think that the parameters below first specify an image,
            //  then they specify a rectangle within the image, and then they
            //  specify a rectangle on the canvas. Image data is copied from the
            //  rectangle inside the image to the rectangle on the canvas.

            canvas.drawBitmap(
                spriteImage,

                Rect(
                    frameWidth * currentFrameIndex,
                    0,
                    frameWidth * (currentFrameIndex + 1) - 1,
                    frameHeight - 1
                ),

                Rect(
                    spriteCenterPointX - frameWidth / 2,
                    spriteCenterPointY - frameHeight / 2,
                    spriteCenterPointX + frameWidth / 2,
                    spriteCenterPointY + frameHeight / 2
                ),
                null
            )

            currentFrameIndex++

            if (currentFrameIndex >= numberOfFramesToShow) {
                spriteState = SPRITE_HAS_FINISHED
            }
        } else if (spriteState == DELAYING_BEFORE_SHOW) {
            numberOfFramesToDelay--

            if (numberOfFramesToDelay <= 0) {
                spriteState = SPRITE_IS_ALIVE
            }
        }
    }

    companion object {
        const val DELAYING_BEFORE_SHOW: Int = 1
        const val SPRITE_IS_ALIVE: Int = 2
        const val SPRITE_HAS_FINISHED: Int = 3
    }
}

class RandomExplosionsView(
    context: Context,
    attrs : AttributeSet ? = null
) : SurfaceView(context, attrs), Runnable,
    SurfaceHolder.Callback {
    private var animationThread: Thread? = null

    private var threadMustBeExecuted: Boolean = false

    private var backgroundPaint: Paint = Paint()

    private var explosionFramesImage: Bitmap

    private var explosionsToShow: ArrayList<Sprite> = ArrayList()

    private var viewWidth: Int = 0
    private var viewHeight: Int = 0

    init {
        // Setting the background would probably hide the Surface.
        //  setBackgroundColor( 0xFFFFF5EE ) ; // Light color
        backgroundPaint.style = Paint.Style.FILL
        backgroundPaint.color = -0xd0b0b1

        // With the BitmapFactory.Options we'll prevent scaling of the original image.
        val bitmapFactoryOptions = BitmapFactory.Options()

        bitmapFactoryOptions.inScaled = false

        explosionFramesImage = BitmapFactory.decodeResource(
            context.resources, R.drawable.explosion, //frames_72_87x87
            bitmapFactoryOptions
        )

        holder.addCallback(this)
    }

    fun setViewWidth(w : Int) {
        viewWidth = w
    }

    fun setViewHeight(h : Int) {
        viewHeight = h
    }


    private fun createRandomExplosions(numberOfExplosions: Int) {
        for (explosionCounter in 0 until numberOfExplosions) {
            // With some 'mathematics' we'll ensure that the explosions do not take
            // place very close to the edges of the screen.

            val randomExplosionPositionX = (Math.random() * (viewWidth - 80)).toInt() + 40
            val randomExplosionPositionY = (Math.random() * (viewHeight - 80)).toInt() + 40

            val framesToDelayBeforeShow = (Math.random() * 96).toInt()

            val newExplosion = Sprite(
                explosionFramesImage,
                72,
                randomExplosionPositionX,
                randomExplosionPositionY,
                framesToDelayBeforeShow
            )

            explosionsToShow.add(newExplosion)
        }
    }

    fun startAnimationThread() {
        if (animationThread == null) {
            animationThread = Thread(this)

            threadMustBeExecuted = true

            animationThread!!.start()
        }

        print("\n Method start() executed. ")
    }

    fun stopAnimationThread() {
        if (animationThread != null) {
            animationThread!!.interrupt()

            threadMustBeExecuted = false

            animationThread = null
        }

        print("\n Method stop() executed. ")
    }

    override fun run() {
        print("\n Method run() started.")

        while (threadMustBeExecuted) {
            try {
                Thread.sleep(30)
            } catch (caughtException: InterruptedException) {
                threadMustBeExecuted = false
            }

            // We'll check all the explosions if their sprites have finished
            // their animation.
            var explosionIndex = 0

            while (explosionIndex < explosionsToShow.size) {
                if (explosionsToShow[explosionIndex].sprite_has_finished()) {
                    // With the remove() method we remove an object from the array.

                    explosionsToShow.removeAt(explosionIndex)
                } else {
                    explosionIndex++
                }
            }

            // We'll limit the possible maximum number of explosions on the screen.
            if (explosionsToShow.size < 120) {
                createRandomExplosions(Random.nextInt(1, 3))
            }

            // Next, we'll draw the explosions.
            val canvas = holder.lockCanvas()

            if (canvas != null) {
                canvas.drawPaint(backgroundPaint)

                for (explosion in explosionsToShow) {
                    explosion.draw(canvas)
                }

                holder.unlockCanvasAndPost(canvas)
            }
        }

        print("\n Method run() stopped. ")
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        print("\n in SurfaceChanged()")
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        print("\n in SurfaceCreated()")
        startAnimationThread()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        print("\n in SurfaceDestroyed()")
        stopAnimationThread()
    }
}


class RandomExplosionsActivity : Activity() {
    private var randomExplosionsView: RandomExplosionsView? = null

    @RequiresApi(Build.VERSION_CODES.R)
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val display = this.display

        val displaySize = Point()

        display?.getSize(displaySize)

        randomExplosionsView = RandomExplosionsView(this)

        randomExplosionsView?.apply {
            setViewWidth(displaySize.x)
            setViewHeight(displaySize.y)
        }


        setContentView(randomExplosionsView)
    }

    override fun onStop() {
        super.onStop()
        randomExplosionsView?.startAnimationThread()
    }

    override fun onPause() {
        super.onPause()
        randomExplosionsView?.stopAnimationThread()
    }
}

