package com.alessandro.exocustomcomp.customView

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.alessandro.exocustomcomp.R

class CustomComponent(context: Context, attrs: AttributeSet): ConstraintLayout(context, attrs) {

    private var editText: EditText
    private var imgView: ImageView

    init {
        View.inflate(context, R.layout.custom_component, this)

        editText = findViewById(R.id.textView)
        imgView = findViewById(R.id.imgView)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomComponent,
            0, 0).apply {
                editText.setText(getString(R.styleable.CustomComponent_text))
                editText.setTextColor(getColor(R.styleable.CustomComponent_textColor, Color.GRAY))
                imgView.setImageResource(getResourceId(R.styleable.CustomComponent_src, R.drawable.pepe))
        }.recycle()

    }
}