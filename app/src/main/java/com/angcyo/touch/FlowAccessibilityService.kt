package com.angcyo.touch

import android.graphics.Path
import android.view.WindowManager
import com.angcyo.lib.L
import com.angcyo.uiview.less.accessibility.BaseAccessibilityService
import com.angcyo.uiview.less.accessibility.touch
import com.yhao.floatwindow.FloatWindow

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2018/10/18
 */
class FlowAccessibilityService : BaseAccessibilityService() {

    companion object {

        @Volatile
        var run = false
            set(value) {
                field = value

                if (value) {
                    FloatWindow.get(Touch.TAG)?.let {
                        it.floatView?.let {
                            it.updateFlags(
                                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                                        or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                                        or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                        or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                                        or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                            )
                        }
                    }
                } else {
                    FloatWindow.get(Touch.TAG)?.let {
                        it.floatView?.let {
                            it.updateFlags(
                                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                                        or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                        or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                                        or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                            )
                        }
                    }
                }
            }

        var isServiceConnected = false
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        isServiceConnected = true

        Touch.show(applicationContext)
        SwitchFloat.show(applicationContext)

        Thread {
            while (isServiceConnected) {
                if (run) {
                    touch(
                        arrayOf(Path().apply {
                            moveTo(Touch.getX().toFloat(), Touch.getY().toFloat())
                        }, Path().apply {
                            moveTo(Touch.getX().toFloat(), Touch.getY().toFloat())
                        }, Path().apply {
                            moveTo(Touch.getX().toFloat(), Touch.getY().toFloat())
                        }),
                        longArrayOf(0, 10, 20), longArrayOf(20, 20, 20)
                    )
                }
                Thread.sleep(100)
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        isServiceConnected = false
        SwitchFloat.hide()
        Touch.hide()
        L.e("onDestroy")
    }
}