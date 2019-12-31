package io.github.wongxd.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

import io.github.wongxd.skulibray.specSelect.SpecSelectFragment;
import io.github.wongxd.skulibray.specSelect.bean.SpecBean;
import io.github.wongxd.skulibray.specSelect.listener.ShowGoodImgListener;
import io.github.wongxd.skulibray.specSelect.listener.SubmitSpecCombListener;
import io.github.wongxd.skulibray.specSelect.sku.ProductModel;

/**
 * Created by wongxd on 2018/2/8.
 */

public class AtyJavaActivity extends AppCompatActivity {


    private TextView tv;
    private ImageView ivMain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_main);
        tv = findViewById(R.id.tv);
        ivMain = findViewById(R.id.iv_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doMainLogic();
            }
        });

        Glide.with(AtyJavaActivity.this)
                .load("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1282625489,100434574&fm=27&gp=0.jpg")
                .placeholder(R.drawable.ic_launcher)
                .into(ivMain);
    }

    public void doMainLogic() {
        SpecBean bean = new Gson().fromJson(json, SpecBean.class);


        SpecSelectFragment.showDialog(this, "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1282625489,100434574&fm=27&gp=0.jpg", null,bean,"该规格已无库存！")
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
    }


    String json = "{\n" +
            "    \"attrs\": [\n" +
            "        {\n" +
            "            \"key\": \"颜色\",\n" +
            "            \"value\": [\n" +
            "                {\n" +
            "                    \"id\": 3,\n" +
            "                    \"name\": \"红色\",\n" +
            "                    \"ownId\": 1\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 4,\n" +
            "                    \"name\": \"蓝色\",\n" +
            "                    \"ownId\": 1\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"key\": \"重量\",\n" +
            "            \"value\": [\n" +
            "                {\n" +
            "                    \"id\": 5,\n" +
            "                    \"name\": \"10KG\",\n" +
            "                    \"ownId\": 2\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 6,\n" +
            "                    \"name\": \"20KG\",\n" +
            "                    \"ownId\": 2\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 7,\n" +
            "                    \"name\": \"30KG\",\n" +
            "                    \"ownId\": 2\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"key\": \"产地\",\n" +
            "            \"value\": [\n" +
            "                {\n" +
            "                    \"id\": 24,\n" +
            "                    \"name\": \"江油\",\n" +
            "                    \"ownId\": 22\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 23,\n" +
            "                    \"name\": \"绵阳\",\n" +
            "                    \"ownId\": 22\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 31,\n" +
            "                    \"name\": \"四川绵阳市涪城区的撒啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊\",\n" +
            "                    \"ownId\": 22\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"key\": \"尺寸\",\n" +
            "            \"value\": [\n" +
            "                {\n" +
            "                    \"id\": 20,\n" +
            "                    \"name\": \"30cm\",\n" +
            "                    \"ownId\": 14\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 19,\n" +
            "                    \"name\": \"20cm\",\n" +
            "                    \"ownId\": 14\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 18,\n" +
            "                    \"name\": \"10cm\",\n" +
            "                    \"ownId\": 14\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    ],\n" +
            "    \"combs\": [\n" +
            "        {\n" +
            "            \"comb\": \"4,6,23,20\",\n" +
            "            \"desc\": \"蓝色-20KG-绵阳-30cm\",\n" +
            "            \"id\": 10,\n" +
            "            \"price\": 1.0,\n" +
            "            \"productId\": 5,\n" +
            "            \"stock\": 2,\n" +
            "            \"specImg\":\"http://ww3.sinaimg.cn/mw600/0073ob6Pgy1fo91049t9mj30lc0w0dnh.jpg\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"comb\": \"4,5,23,19\",\n" +
            "            \"desc\": \"蓝色-10KG-绵阳-20cm\",\n" +
            "            \"id\": 8,\n" +
            "            \"price\": 22.0,\n" +
            "            \"productId\": 5,\n" +
            "            \"stock\": 333,\n" +
            "             \"specImg\":\"http://wx4.sinaimg.cn/mw600/0072bW0Xly1fo908gkyqjj30en0miabp.jpg\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"comb\": \"4,5,24,19\",\n" +
            "            \"desc\": \"蓝色-10KG-江油-20cm\",\n" +
            "            \"id\": 9,\n" +
            "            \"price\": 1.0,\n" +
            "            \"productId\": 5,\n" +
            "            \"stock\": 2,\n" +
            "             \"specImg\":\"http://wx1.sinaimg.cn/mw600/0072bW0Xly1fo8zz4znwyj30hq0qoabz.jpg\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"comb\": \"3,6,24,18\",\n" +
            "            \"desc\": \"红色-20KG-江油-10\",\n" +
            "            \"id\": 11,\n" +
            "            \"price\": 1.0,\n" +
            "            \"productId\": 5,\n" +
            "            \"stock\": 2,\n" +
            "            \"specImg\":\"http://wx2.sinaimg.cn/mw600/0072bW0Xly1fo8zf0pn15j30ia0tzdox.jpg\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";
}
