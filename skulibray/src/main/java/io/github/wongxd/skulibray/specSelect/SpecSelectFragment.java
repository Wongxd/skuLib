package io.github.wongxd.skulibray.specSelect;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.wongxd.skulibray.R;
import io.github.wongxd.skulibray.specSelect.bean.SpecBean;
import io.github.wongxd.skulibray.specSelect.custom.DensityUtil;
import io.github.wongxd.skulibray.specSelect.custom.SpaceItemDecoration;
import io.github.wongxd.skulibray.specSelect.custom.SpecLayoutManager;
import io.github.wongxd.skulibray.specSelect.custom.TU;
import io.github.wongxd.skulibray.specSelect.listener.ShowGoodImgListener;
import io.github.wongxd.skulibray.specSelect.listener.SubmitSpecCombListener;
import io.github.wongxd.skulibray.specSelect.observer.IObservable;
import io.github.wongxd.skulibray.specSelect.observer.IObserver;
import io.github.wongxd.skulibray.specSelect.observer.ObserverEventCode;
import io.github.wongxd.skulibray.specSelect.observer.ObserverHolder;
import io.github.wongxd.skulibray.specSelect.sku.ItemClickListener;
import io.github.wongxd.skulibray.specSelect.sku.ProductModel;
import io.github.wongxd.skulibray.specSelect.sku.SkuAdapter;
import io.github.wongxd.skulibray.specSelect.sku.UiData;
import io.github.wongxd.skulibray.specSelect.sku.skuLib.Sku;
import io.github.wongxd.skulibray.specSelect.sku.skuLib.model.BaseSkuModel;


/**
 * wongxd
 * <p>
 * 参考 http://blog.csdn.net/wbwjx/article/details/50507344
 */
public class SpecSelectFragment extends android.support.v4.app.DialogFragment implements IObserver {
    public static final String TAG = "SpecSelectFragment";


    private ImageView iv;
    private TextView tvPrice;
    private TextView tvStock;
    private TextView tvSpec;
    private TextView tvNum;
    private LinearLayout llSpecContainer;

    //展示商品图片
    private ShowGoodImgListener showGoodImgListener;

    //提交用户选择的规格
    private SubmitSpecCombListener submitSpecCombListener;

    private static SpecSelectFragment newInstance() {
        Bundle args = new Bundle();
        SpecSelectFragment fragment = new SpecSelectFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        必须在onCreate 中设置Style ,而在OnCreateView 中设置无效,因为此时对话框已经init了
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomDialog);
        ObserverHolder.getInstance().register(this);
    }

    @Override
    public void onDestroyView() {
        ObserverHolder.getInstance().unregister(this);
        showGoodImgListener = null;
        submitSpecCombListener = null;
        bean = null;
        goodImgPath = null;
        super.onDestroyView();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = initView();


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.BottomDialog);

        builder.setView(view);

        AlertDialog dialog = builder.create();

        dialog.setCanceledOnTouchOutside(true);

        // 设置宽度为屏宽、靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        //设置没有效果
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);

        initData(bean);

        return dialog;
    }

    @NonNull
    private View initView() {
        View view = View.inflate(getActivity(), R.layout.fgt_spec_select, null);

        iv = (ImageView) view.findViewById(R.id.iv);
        ImageView ivClose = (ImageView) view.findViewById(R.id.iv_close);

        tvPrice = (TextView) view.findViewById(R.id.tv_price);
        tvStock = (TextView) view.findViewById(R.id.tv_stock);
        tvSpec = (TextView) view.findViewById(R.id.tv_spec);

        ImageView ivReduce = (ImageView) view.findViewById(R.id.iv_reduce);
        ImageView ivAdd = (ImageView) view.findViewById(R.id.iv_add);
        tvNum = (TextView) view.findViewById(R.id.tv_num_edit);

        TextView tvSure = (TextView) view.findViewById(R.id.tv_sure);

        llSpecContainer = (LinearLayout) view.findViewById(R.id.ll_spec_container);


        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });


        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doNumLogic(true);
            }
        });

        ivReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doNumLogic(false);
            }
        });

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSure();
            }
        });


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        //全屏
        Dialog dialog = getDialog();
        if (null != dialog && dialog.getWindow() != null) {
            dialog.getWindow().setLayout(-1, -2);
        }
    }

    /**
     * 确认选择的规格
     */
    private void doSure() {
        if (specId.equals("null")) {
            TU.t(tvSpec.getText().toString());
            return;
        }
        Integer num = Integer.valueOf(tvNum.getText().toString().trim());
        if (productStock < num) {
            TU.t("库存不足");
            return;
        }

        SpecBean.CombsBean combsBean = getCombsBean();
        if (submitSpecCombListener != null && combsBean != null) {
            submitSpecCombListener.onSubmit(combsBean, num);
        }

        getDialog().dismiss();

    }

    @Nullable
    private SpecBean.CombsBean getCombsBean() {
        SpecBean.CombsBean combsBean = null;
        for (SpecBean.CombsBean c : bean.getCombs()) {
            if (specId.equals(c.getComb())) {
                combsBean = c;
            }
        }
        return combsBean;
    }


    /**
     * 数量的加减
     *
     * @param isAdd
     */
    private void doNumLogic(boolean isAdd) {
        String numS = tvNum.getText().toString().trim();
        if (TextUtils.isEmpty(numS)) {
            tvNum.setText("1");
            return;
        }
        long num = Long.valueOf(numS);
        if (isAdd) {
            num++;
        } else {
            if (num <= 1) num = 1;
            else
                num--;
        }
        tvNum.setText(num + "");
    }

    private UiData mUiData;
    private ProductModel products;


    private void doShowSpecTips(String s) {
        tvSpec.setText(s);
    }

    /**
     * 选择的规格id
     */
    private String specId = "null";

    private String price = "";

    private long productStock = 0;


    private void doShowSpecId(String s) {
        specId = s;
        if (s.equals("null")) {
            tvStock.setText("");
            tvPrice.setText("");
            return;
        }
        s = s.substring(0, s.length() - 1);
        specId = s;
        productStock = mUiData.getResult().get(s).getStock();
        price = mUiData.getResult().get(s).getPrice();
        tvPrice.setText("￥ " + price);
        tvStock.setText("库存 " + productStock);
        try {
            String specImgPath = getCombsBean().getSpecImg();
            if (showGoodImgListener != null) {
                showGoodImgListener.displayImg(iv, specImgPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Log.d(TAG, "specid " + s + " price" + mUiData.getResult().get(s).getPrice() + " stock" + mUiData.getResult().get(s).getStock());
    }

    /**
     * 用户点击 做出了选择
     *
     * @param observable 消息发布者
     * @param msg        发布的消息内容
     * @param flag       消息的类型标记
     */
    @Override
    public void onMessageReceived(IObservable observable, Object msg, int flag) {

        String info = (String) msg;
        switch (flag) {
            case ObserverEventCode.SKU_GETSPECID:
                doShowSpecId(info);
                break;

            case ObserverEventCode.SKU_TIPS:
                doShowSpecTips(info);
                break;

            default:
                break;
        }
    }


    /**
     * 初始化展示的数据
     */
    private void initData(SpecBean bean) {

        if (showGoodImgListener != null) {
            showGoodImgListener.displayImg(iv, goodImgPath);
        }

        List<SpecBean.AttrsBean> attrs = bean.getAttrs();

        List<ProductModel.AttributesEntity> pAttr = new ArrayList<>();
        int groupId = 0;
        for (SpecBean.AttrsBean attr : attrs) {
            ProductModel.AttributesEntity group = new ProductModel.AttributesEntity();
            group.setName(attr.getKey());
            group.setId(groupId);
            for (SpecBean.AttrsBean.ValueBean item : attr.getValue()) {
                group.getAttributeMembers()
                        .add(new ProductModel.AttributesEntity.AttributeMembersEntity(groupId, item.getId(), item.getName()));
            }
            pAttr.add(group);
            groupId++;
        }

        List<SpecBean.CombsBean> comb = bean.getCombs();
        Map<String, BaseSkuModel> initData = new HashMap<>();
        for (SpecBean.CombsBean g : comb) {
            initData.put(g.getComb(),
                    new BaseSkuModel(g.getPrice() + "", g.getStock()));
        }

        /**
         * 所有规格 的 组名
         */
        List<String> groupNameList = new ArrayList<String>();
        for (SpecBean.AttrsBean s : attrs) {
            groupNameList.add(s.getKey());
        }

        mUiData = new UiData();
        mUiData.setGroupNameList(groupNameList);
        products = new ProductModel();

        products.setProductStocks(initData);
        products.setAttributes(pAttr);


        SpaceItemDecoration decoration = new SpaceItemDecoration(DensityUtil.dp2px(3f), DensityUtil.dp2px(6f));
        //添加list组
        for (int i = 0; i < products.getAttributes().size(); i++) {
            View viewList = View.inflate(getContext(), R.layout.bottom_sheet_group, null);
            TextView tvTitle = (TextView) viewList.findViewById(R.id.tv_title);
            RecyclerView recyclerViewBottom = (RecyclerView) viewList.findViewById(R.id.recycler_bottom);
            SkuAdapter skuAdapter = new SkuAdapter(products.getAttributes().get(i).getAttributeMembers(), products.getAttributes().get(i).getName());
            mUiData.getAdapters().add(skuAdapter);

            SpecLayoutManager layoutManager = new SpecLayoutManager();
            layoutManager.setAutoMeasureEnabled(true);   //必须，防止recyclerview高度为wrap时测量item高度0

            recyclerViewBottom.addItemDecoration(decoration);
            recyclerViewBottom.setLayoutManager(layoutManager);
            recyclerViewBottom.setAdapter(skuAdapter);

            tvTitle.setText(products.getAttributes().get(i).getName());
            llSpecContainer.addView(viewList);
        }
        // SKU 计算
        mUiData.setResult(Sku.skuCollection(products.getProductStocks()));

        for (String key : mUiData.getResult().keySet()) {
            Log.d("SKU Result", "key = " + key + " value = " + mUiData.getResult().get(key));
        }

        //设置点击监听器
        for (SkuAdapter adapter : mUiData.getAdapters()) {
            ItemClickListener listener = new ItemClickListener(mUiData, adapter);
            adapter.setOnClickListener(listener);
        }
        // 初始化按钮
        for (int i = 0; i < mUiData.getAdapters().size(); i++) {
            for (ProductModel.AttributesEntity.AttributeMembersEntity entity : mUiData.getAdapters().get(i).getAttributeMembersEntities()) {
                if (mUiData.getResult().get(entity.getAttributeMemberId() + "") == null
                        || mUiData.getResult().get(entity.getAttributeMemberId() + "").getStock() <= 0) {
                    entity.setStatus(2);
                }
            }
        }


    }


    private static String goodImgPath;
    private static SpecBean bean;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////外部调用方法///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 可以避免重复
     *
     * @param aty
     * @param goodImg  商品展示的图片
     * @param specBean 规格对象 {@link io.github.wongxd.skulibray.specSelect.bean.SpecBean}
     * @return
     */
    public static SpecSelectFragment showDialog(AppCompatActivity aty, String goodImg, SpecBean specBean) {

        TU.register(new WeakReference<Context>(aty.getApplicationContext()));
        goodImgPath = goodImg;
        bean = specBean;
        FragmentManager fragmentManager = aty.getSupportFragmentManager();
        SpecSelectFragment bottomDialogFragment =
                (SpecSelectFragment) fragmentManager.findFragmentByTag(TAG);
        if (null == bottomDialogFragment) {
            bottomDialogFragment = newInstance();
        }

        if (!aty.isFinishing()
                && null != bottomDialogFragment
                && !bottomDialogFragment.isAdded()) {
            fragmentManager.beginTransaction()
                    .add(bottomDialogFragment, TAG)
                    .commitAllowingStateLoss();
        }

        return bottomDialogFragment;
    }


    /**
     * 设置展示图片的listener
     *
     * @param showGoodImgListener
     */
    public SpecSelectFragment setShowGoodImgListener(ShowGoodImgListener showGoodImgListener) {
        this.showGoodImgListener = showGoodImgListener;
        return this;
    }


    /**
     * 设置 规格选择完成 后 提交 时的回掉
     *
     * @param submitSpecCombListener
     */
    public SpecSelectFragment setSubmitSpecCombListener(SubmitSpecCombListener submitSpecCombListener) {
        this.submitSpecCombListener = submitSpecCombListener;
        return this;
    }


}
