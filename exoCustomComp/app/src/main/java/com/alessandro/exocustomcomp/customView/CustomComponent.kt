package com.alessandro.exocustomcomp.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.*

class CustomComponent(context: Context, attrs: AttributeSet): View(context, attrs) {

    private val src: String = ""
    private val text: String = ""
    private val textColor = Color.GRAY

    //private val textView: TextView


    init {
        SetupAttributes(attrs)
    }



    private fun SetupAttributes(attrs: AttributeSet?){
        val typedArray = context.theme.obtainStyledAttributes(attrs,R.styleable.clearFindViewByIdCache())
    }


}