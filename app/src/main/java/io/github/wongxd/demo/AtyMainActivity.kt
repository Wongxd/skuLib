package io.github.wongxd.demo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.google.gson.Gson
import io.github.wongxd.skulibray.specSelect.SpecSelectFragment
import io.github.wongxd.skulibray.specSelect.bean.SpecBean
import kotlinx.android.synthetic.main.aty_main.*

class AtyMainActivity : AppCompatActivity() {

    companion object {
        val TAG: String = AtyMainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.aty_main)
        btn_java.visibility = View.VISIBLE
        btn_java.setOnClickListener { startActivity(Intent(this, AtyJavaActivity::class.java)) }

        btn.setOnClickListener { doLogic() }
    }

    private fun doLogic() {
        val goodImgUrl = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1282625489,100434574&fm=27&gp=0.jpg"
        Glide.with(this).load(goodImgUrl).placeholder(R.drawable.ic_launcher).into(iv_main)

        SpecSelectFragment.showDialog(this, goodImgUrl, spec)
                .setShowGoodImgListener { iv, imgUrl ->
                    Log.e(TAG, "商品图片地址= $imgUrl    iv对象--$iv")
                    Glide.with(this).load(imgUrl).placeholder(R.drawable.ic_launcher).centerCrop().into(iv)

                }
                .setSubmitSpecCombListener { combBean, num ->
                    Log.e(TAG, " 描述---${combBean.desc}      数量---$num")
                    tv.text = " 描述---${combBean.desc}---数量---$num"
                }
    }

    val spec: SpecBean by lazy { Gson().fromJson(json, SpecBean::class.java) }

    val json = """
        {
    "attrs": [
        {
            "key": "颜色",
            "value": [
                {
                    "id": 3,
                    "name": "红色",
                    "ownId": 1
                },
                {
                    "id": 4,
                    "name": "蓝色",
                    "ownId": 1
                }
            ]
        },
        {
            "key": "重量",
            "value": [
                {
                    "id": 5,
                    "name": "10KG",
                    "ownId": 2
                },
                {
                    "id": 6,
                    "name": "20KG",
                    "ownId": 2
                },
                {
                    "id": 7,
                    "name": "30KG",
                    "ownId": 2
                }
            ]
        },
        {
            "key": "产地",
            "value": [
                {
                    "id": 24,
                    "name": "江油",
                    "ownId": 22
                },
                {
                    "id": 23,
                    "name": "绵阳",
                    "ownId": 22
                },
                {
                    "id": 31,
                    "name": "四川绵阳市涪城区的撒啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊",
                    "ownId": 22
                }
            ]
        },
        {
            "key": "尺寸",
            "value": [
                {
                    "id": 20,
                    "name": "30cm",
                    "ownId": 14
                },
                {
                    "id": 19,
                    "name": "20cm",
                    "ownId": 14
                },
                {
                    "id": 18,
                    "name": "10cm",
                    "ownId": 14
                }
            ]
        }
    ],
    "combs": [
        {
            "comb": "4,6,23,20",
            "desc": "蓝色-20KG-绵阳-30cm",
            "id": 10,
            "price": 1.0,
            "productId": 5,
            "stock": 2,
            "specImg":"http://ww3.sinaimg.cn/mw600/0073ob6Pgy1fo91049t9mj30lc0w0dnh.jpg"
        },
        {
            "comb": "4,5,23,19",
            "desc": "蓝色-10KG-绵阳-20cm",
            "id": 8,
            "price": 22.0,
            "productId": 5,
            "stock": 333,
             "specImg":"http://wx4.sinaimg.cn/mw600/0072bW0Xly1fo908gkyqjj30en0miabp.jpg"
        },
        {
            "comb": "4,5,24,19",
            "desc": "蓝色-10KG-江油-20cm",
            "id": 9,
            "price": 1.0,
            "productId": 5,
            "stock": 2,
             "specImg":"http://wx1.sinaimg.cn/mw600/0072bW0Xly1fo8zz4znwyj30hq0qoabz.jpg"
        },
        {
            "comb": "3,6,24,18",
            "desc": "红色-20KG-江油-10",
            "id": 11,
            "price": 1.0,
            "productId": 5,
            "stock": 2,
            "specImg":"http://wx2.sinaimg.cn/mw600/0072bW0Xly1fo8zf0pn15j30ia0tzdox.jpg"
        }
    ]
}
    """.trimIndent()
}
