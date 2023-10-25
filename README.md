# skuLib

quickly implement product SKU on Android.

快速实现商品规格选择。

实现类似淘宝规格选择的效果。


源码有较为详细的注释，更多信息请查看源码。


![演示](/art/demo.gif)

***

###添加依赖

[![](https://jitpack.io/v/Wongxd/skuLib.svg)](https://jitpack.io/#Wongxd/skuLib)
      
     // 你的项目中一定要使用recycleview
    implementation 'com.android.support:recyclerview-v7:x.x.x'

    // 加入本依赖库
 	// x.y.z 替换成具体的release版本号 如：0.0.7
	implementation('com.github.Wongxd:skuLib:x.y.z') { 
		exclude group: 'com.android.support'
    }





***
###使用方式
  
 kotlin:

 		 SpecSelectFragment.showDialog(this, null, defaultAttrList, spec)
                        .setShowGoodImgListener { iv, imgUrl ->
                            Log.e(TAG, "商品图片地址= $imgUrl    iv对象--$iv")
                            Glide.with(this).load(imgUrl).placeholder(R.drawable.ic_launcher).centerCrop().into(iv)

                        }
                        .setSubmitSpecCombListener { combBean, num, statusRestoreList ->
                            defaultAttrList = statusRestoreList
                            Log.e(TAG, " 描述---${combBean.desc}      数量---$num")
                            tv.text = " 描述---${combBean.desc}---数量---$num"
                        }



java:


		SpecSelectFragment.showDialog(this, "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1282625489,100434574&fm=27&gp=0.jpg", bean)
                        .setShowGoodImgListener(new ShowGoodImgListener() {
                            @Override
                            public void displayImg(ImageView iv, String imgUrl) {
                                Glide.with(AtyJavaActivity.this).load(imgUrl)
                                        .placeholder(R.drawable.ic_launcher).into(iv);
                            }
                        })
                        .setSubmitSpecCombListener(new SubmitSpecCombListener() {
                            @Override
                            public void onSubmit(SpecBean.CombsBean combBean, int num, List<ProductModel.AttributesEntity.AttributeMembersEntity> statusRestoreList) {
                                tv.setText("描述" + combBean.getDesc() + "----数量" + num);
                            }
                        });





















[参考1--Android DialogFragment实现底部弹出菜单效果](http://blog.csdn.net/wbwjx/article/details/50507344)

[参考2-- 利用观察者模式（发布/订阅模式）制作一个“代替”广播的通知类](http://blog.csdn.net/chengliang0315/article/details/53381539)

[参考3--Sku算法--商城（品种，规格，参数等选择）](http://blog.csdn.net/u012589795/article/details/53304287)
