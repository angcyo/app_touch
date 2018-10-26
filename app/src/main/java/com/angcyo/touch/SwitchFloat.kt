package com.angcyo.touch

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.CompoundButton
import com.yhao.floatwindow.FloatWindow
import com.yhao.floatwindow.Screen

/**
 * Created by angcyo on 2018/10/25 21:38
 */
@SuppressLint("StaticFieldLeak")
object SwitchFloat {
    var view: View? = null
    val TAG = "SwitchFloat"

    fun show(context: Context) {
        if (view == null) {
            view = FloatWindow.with(context)
                .setTag(TAG)
                .setView(R.layout.view_switch)
                .setX(Screen.width, 0.8f)
                .setY(Screen.height, 0.5f)
                .build(true)

            val switchCompat: CompoundButton = view!!.findViewById(R.id.switch_button)
            switchCompat.setOnCheckedChangeListener { buttonView, isChecked ->
                FlowAccessibilityService.run = isChecked
            }
        }
    }

    fun hide() {
        view = null
        FloatWindow.destroy(TAG)
    }

    fun close() {
        view?.let {
            val switchCompat: CompoundButton = it.findViewById(R.id.switch_button)
            switchCompat.isChecked = false
        }
    }
}