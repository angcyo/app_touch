package com.angcyo.touch

import android.graphics.Path
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
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

        //点击事件 间隔
        var touchInterval = 20L
        //点击事件 持续
        var touchDuration = 20L
    }

    /**切换应用, 暂停操作*/
    private var lastPackageName = ""

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

//                            moveTo(Touch.getX().toFloat() + 10, Touch.getY().toFloat() + 10)
                        }),
                        // longArrayOf(0, 200), longArrayOf(160, 160)
                        touchInterval, touchDuration
                    )
                    Thread.sleep((touchInterval + touchDuration) * 2 + touchInterval)
                } else {
                    Thread.sleep(1000)
                    Thread.yield()
                }
            }
        }.start()

        MainActivity.loadConfig()
    }

    override fun onWindowStateChanged(event: AccessibilityEvent) {
        super.onWindowStateChanged(event)
        L.e("切换到:${event.packageName}")

        if (lastPackageName.isNotEmpty() && !lastPackageName.equals("${event.packageName}", true)) {
            //切换了应用
            SwitchFloat.close()
        }
        lastPackageName = "${event.packageName}"
    }

    override fun onDestroy() {
        super.onDestroy()
        isServiceConnected = false
        SwitchFloat.hide()
        Touch.hide()
        L.e("onDestroy")
    }
}