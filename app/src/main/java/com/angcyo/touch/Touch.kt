package com.angcyo.touch

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import com.yhao.floatwindow.FloatWindow
import com.yhao.floatwindow.Screen

/**
 * Created by angcyo on 2018/10/25 21:38
 */
@SuppressLint("StaticFieldLeak")
object Touch {
    var touchView: View? = null
    val TAG = "touch"
    fun show(context: Context) {
        if (touchView == null) {
            touchView = FloatWindow.with(context)
                .setTag(TAG)
                .setView(R.layout.view_touch)
                .setX(Screen.width, 0.3f)
                .setY(Screen.height, 0.4f)
                .build(true)
        }
    }

    fun getY(): Int {
        val offset = if (touchView == null) {
            0
        } else {
            touchView!!.measuredHeight / 2
        }
        return (FloatWindow.get(TAG)?.y ?: 0) + offset
    }

    fun getX(): Int {
        val offset = if (touchView == null) {
            0
        } else {
            touchView!!.measuredWidth / 2
        }
        return (FloatWindow.get(TAG)?.x ?: 0) + offset
    }

    fun hide() {
        touchView = null
        FloatWindow.destroy(TAG)
    }
}