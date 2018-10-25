package com.angcyo.touch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.angcyo.flow.http.ApiService
import com.angcyo.http.Http
import com.angcyo.http.HttpSubscriber
import com.angcyo.touch.http.bean.ConfigBean
import com.angcyo.uiview.less.accessibility.Permission

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

        loadConfig()
    }

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
