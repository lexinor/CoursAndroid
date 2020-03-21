package com.alessandro.coursandroid1

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

const val HAPPY = 0L
const val SAD = 1L

class SmileyView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val paint = Paint()
    private val mouthPath = Path()

    private var faceColor = Color.BLUE
    private var borderWidth = 4f

    private var happinessState = HAPPY

    init {
        setupAttributes(attrs)
    }

    private fun setupAttributes(attrs: AttributeSet?) {
        val typeArray = context.theme.obtainStyledAttributes(attrs,R.styleable.SmileyView,0,0)

        borderWidth = typeArray.getDimension(R.styleable.SmileyView_borderWidth, 1f)
    }
}