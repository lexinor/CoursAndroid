package com.axt.esgi.esgi4a2020.common

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showError(error: Throwable) =
    Toast.makeText(context, error.localizedMessage, Toast.LENGTH_SHORT).show()