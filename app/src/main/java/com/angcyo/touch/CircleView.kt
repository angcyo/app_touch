package com.angcyo.touch

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.angcyo.uiview.less.kotlin.debugPaint
import com.angcyo.uiview.less.kotlin.getColor

/**
 * Created by angcyo on 2018/10/25 21:46
 */

class CircleView(context: Context, attributeSet: AttributeSet? = null) : View(context, attributeSet) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            val r: Float = (measuredWidth / 2).toFloat()
            debugPaint.style = Paint.Style.FILL
            debugPaint.color = getColor(R.color.colorAccent)
            it.drawCircle(r, r, r, debugPaint)
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }
}
