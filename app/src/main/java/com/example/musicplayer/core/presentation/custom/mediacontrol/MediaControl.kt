package com.example.musicplayer.core.presentation.custom.mediacontrol

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.musicplayer.R

class MediaControl : ConstraintLayout {

    //Attributes Variables
    var playDrawable: Drawable? = null
    var stopDrawable: Drawable? = null
    var previousDrawable: Drawable? = null
    var nextDrawable: Drawable? = null

    //State variables
    var isPlaying: Boolean = false
    var playstopButtonClick: () -> Unit = {}

    constructor(context: Context) : super(context) {
        layoutInflation()
        listenersApplication()
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        layoutInflation()
        attributeExtraction(context, attrs)
        attributeApplication()
        listenersApplication()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        layoutInflation()
        attributeExtraction(context, attrs)
        attributeApplication()
        listenersApplication()
    }

    private fun layoutInflation() {
        LayoutInflater.from(context).inflate(R.layout.custom_mediacontrol, this)
    }

    private fun attributeExtraction(context: Context, attrs: AttributeSet?) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomMediaControl,
            0, 0
        )

        try {
            playDrawable = a.getDrawable(R.styleable.CustomMediaControl_customMediaControl_drawablePlay)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            stopDrawable = a.getDrawable(R.styleable.CustomMediaControl_customMediaControl_drawableStop)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            previousDrawable = a.getDrawable(R.styleable.CustomMediaControl_customMediaControl_drawablePrevious)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            nextDrawable = a.getDrawable(R.styleable.CustomMediaControl_customMediaControl_drawableNext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        a.recycle()

    }

    private fun attributeApplication(){
        playDrawable.let {
            val ib_playstop = findViewById<ImageButton>(R.id.ib_playStop)
            ib_playstop.background = it
        }
        previousDrawable.let {
            val ib_previous = findViewById<ImageButton>(R.id.ib_previous)
            ib_previous.background = it
        }
        nextDrawable.let {
            val ib_next = findViewById<ImageButton>(R.id.ib_next)
            ib_next.background = it
        }
        invalidate()
        requestLayout()
    }

    private fun listenersApplication(){
        val ib_playstop = findViewById<ImageButton>(R.id.ib_playStop)
        ib_playstop.setOnClickListener {
            if (isPlaying) {
                it.background = stopDrawable
                isPlaying = false
            } else {
                it.background = playDrawable
                isPlaying = true
            }
            playstopButtonClick()
        }
    }

}