package com.example.musicplayer.core.presentation.custom.mediacontrol

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.musicplayer.R

class SingleTrackControl : ConstraintLayout {

    //Attributes Variables
    var playDrawable: Drawable? = null
    var stopDrawable: Drawable? = null

    //State variables
    var isPlaying: Boolean = false
    var currentTitle: String = ""
    var playstopButtonClick: (state: Boolean) -> Unit = {}



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
        LayoutInflater.from(context).inflate(R.layout.custom_singletrackcontrol, this)
    }

    private fun attributeExtraction(context: Context, attrs: AttributeSet?) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomSingleTrackControl,
            0, 0
        )

        try {
            playDrawable = a.getDrawable(R.styleable.CustomSingleTrackControl_singleTrackControl_drawablePlay)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            stopDrawable = a.getDrawable(R.styleable.CustomSingleTrackControl_singleTrackControl_drawableStop)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        a.recycle()

    }

    private fun attributeApplication(){
        try {
            val ib_playstop = findViewById<ImageButton>(R.id.ib_playStop_singletrackcontrol)
            ib_playstop.background = playDrawable
            //ib_playstop.setImageDrawable(playDrawable)
            Log.d("Custom STC", "Applying")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        invalidate()
        requestLayout()
    }

    private fun listenersApplication(){
        val ib_playstop = findViewById<ImageButton>(R.id.ib_playStop_singletrackcontrol)
        ib_playstop.setOnClickListener {
            playstopButtonClick(isPlaying)
            if (isPlaying) {
                it.background = playDrawable
                isPlaying = false
            } else {
                it.background = stopDrawable
                isPlaying = true
            }
        }
        invalidate()
        requestLayout()
    }

    public fun setTrackTitle(title: String) {
        val tv_title = findViewById<TextView>(R.id.tv_title_singletrackcontrol)
        currentTitle = title
        tv_title.text = title
        //invalidate()
        requestLayout()
    }

    public fun setOnPlayStopClickListener(listener: (state: Boolean) -> Unit){
        playstopButtonClick = listener
        requestLayout()
    }

    public fun setPlayButtonIcon(playing: Boolean){
        val ib_playstop = findViewById<ImageButton>(R.id.ib_playStop_singletrackcontrol)
        if (playing){
            ib_playstop.background = stopDrawable
            isPlaying = true
        } else {
            ib_playstop.background = playDrawable
            isPlaying = false
        }
        requestLayout()

    }
}