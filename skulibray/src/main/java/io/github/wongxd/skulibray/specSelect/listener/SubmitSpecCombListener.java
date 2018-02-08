package io.github.wongxd.skulibray.specSelect.listener;

import io.github.wongxd.skulibray.specSelect.bean.SpecBean;

/**
 * Created by wongxd on 2018/2/8.
 * <p>
 * 点击规格选择界面的 确定 按钮，提交所选规格组合的监听
 */

public interface SubmitSpecCombListener {

    /**
     * @param combBean 所选规格的信息  {@link io.github.wongxd.skulibray.specSelect.bean.SpecBean.CombsBean}
     * @param num      选择的数量
     */
    void onSubmit(SpecBean.CombsBean combBean, int num);
}
