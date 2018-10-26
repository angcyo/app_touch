package com.angcyo.touch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.angcyo.flow.http.ApiService
import com.angcyo.http.Http
import com.angcyo.http.HttpSubscriber
import com.angcyo.touch.http.bean.ConfigBean
import com.angcyo.uiview.less.accessibility.Permission
import com.angcyo.uiview.less.kotlin.setInputText
import com.angcyo.uiview.less.widget.SingleTextWatcher

class MainActivity : AppCompatActivity() {

    companion object {
        fun loadConfig() {
            Http.create(ApiService::class.java)
                .config()
                .compose(Http.transformerBean(ConfigBean::class.java))
                .subscribe(object : HttpSubscriber<ConfigBean>() {
                    override fun onEnd(data: ConfigBean?, error: Throwable?) {
                        super.onEnd(data, error)
                        data?.let {
                            if (it.app_touch != 1) {
                                throw RuntimeException("证书已过期")
                            }
                        }
                    }
                })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    val touchIntervalWatcher = object : SingleTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let {
                try {
                    FlowAccessibilityService.touchInterval = it.toString().toLong()
                } catch (e: Exception) {
                }
            }
        }
    }

    val touchDurationWatcher = object : SingleTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let {
                try {
                    FlowAccessibilityService.touchDuration = it.toString().toLong()
                } catch (e: Exception) {
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            if (Permission.check(this)) {

            }
        }

        if (Permission.haveAllPermission(this)) {
            button.text = "欢迎使用"
        } else {
            button.text = "打开权限"
        }

        findViewById<EditText>(R.id.touch_interval).apply {
            setInputText("${FlowAccessibilityService.touchInterval}")
            removeTextChangedListener(touchIntervalWatcher)
            addTextChangedListener(touchIntervalWatcher)
        }
        findViewById<EditText>(R.id.touch_duration).apply {
            setInputText("${FlowAccessibilityService.touchDuration}")
            removeTextChangedListener(touchDurationWatcher)
            addTextChangedListener(touchDurationWatcher)
        }

        loadConfig()
    }
}
