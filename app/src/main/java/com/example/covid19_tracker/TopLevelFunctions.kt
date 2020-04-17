package com.parassidhu.coronavirusapp.util

import android.os.Handler
import android.util.Log
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.covid19_tracker.App

fun cornerRadius(value: Int): RequestOptions {
    return RequestOptions().transform(
        CenterCrop(),
        RoundedCorners(
            dp2px(value.toFloat())
        )
    )
}

fun dp2px(dp: Float): Int {
    //val context = App.applicationContext
    //val scale = context.resources.displayMetrics.density
    return (dp * 1 + 0.5f).toInt()
}


fun runInHandler(timeInMillis: Long, action: () -> Unit) {
    Handler().postDelayed(action, timeInMillis)
}

fun log(message: String?) {
    Log.d("Paras", message)
}