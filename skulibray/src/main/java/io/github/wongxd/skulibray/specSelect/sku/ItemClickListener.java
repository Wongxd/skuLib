package io.github.wongxd.skulibray.specSelect.sku;


import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.github.wongxd.skulibray.specSelect.custom.TU;
import io.github.wongxd.skulibray.specSelect.observer.ObserverEventCode;
import io.github.wongxd.skulibray.specSelect.observer.ObserverHolder;


/**
 * wongxd
 * 参考 http://blog.csdn.net/u012589795/article/details/53304287
 */
public class ItemClickListener implements SkuAdapter.OnClickListener {

    private String TAG = getClass().getSimpleName();

    private UiData mUiData;
    private SkuAdapter mCurrentAdapter;
    private String mNoStockTip;

    public ItemClickListener(UiData uiData, SkuAdapter currentAdapter, @Nullable String noStockTip) {
        mUiData = uiData;
        mCurrentAdapter = currentAdapter;
        mNoStockTip = noStockTip;
    }

    @Override
    public void onItemClickListener(int position) {
        onItemClickListener(mCurrentAdapter.getAttributeMembersEntities().get(position));
    }

    @Override
    public void onItemClickListener(ProductModel.AttributesEntity.AttributeMembersEntity attributeMembersEntity) {
        //屏蔽不可点击
        if (attributeMembersEntity.getStatus() == ProductModel.AttributeMemberStatus.UNCHECKABLE) {
            if (null != mNoStockTip && !TextUtils.isEmpty(mNoStockTip)) {
                TU.t(mNoStockTip);
            }
            return;
        }
        // 设置当前单选点击
        for (ProductModel.AttributesEntity.AttributeMembersEntity entity : mCurrentAdapter.getAttributeMembersEntities()) {
            if (entity.equals(attributeMembersEntity)) {
                String key = mCurrentAdapter.groupName;
                if (mCurrentAdapter.getCurrentSelectedItem() == null || !mCurrentAdapter.getCurrentSelectedItem().equals(entity)) {
                    entity.setStatus(ProductModel.AttributeMemberStatus.CHECKED);
                    //添加已经选择的对象
                    mCurrentAdapter.setCurrentSelectedItem(entity);
                    mUiData.getSelectedMap().put(key, entity);
                } else {
                    entity.setStatus(ProductModel.AttributeMemberStatus.CHECKABLE);
                    mCurrentAdapter.setCurrentSelectedItem(null);
                    mUiData.getSelectedMap().remove(key);
                }
            } else {
                entity.setStatus(entity.getStatus() == ProductModel.AttributeMemberStatus.UNCHECKABLE ? ProductModel.AttributeMemberStatus.UNCHECKABLE :
                        ProductModel.AttributeMemberStatus.CHECKABLE);
            }
        }
        //存放当前被点击的按钮
        mUiData.getSelectedEntities().clear();
        for (int i = 0; i < mUiData.getAdapters().size(); i++) {
            if (mUiData.getAdapters().get(i).getCurrentSelectedItem() != null) {
                mUiData.getSelectedEntities().add(mUiData.getAdapters().get(i).getCurrentSelectedItem());
            }
        }
        //处理未选中的按钮
        for (int i = 0; i < mUiData.getAdapters().size(); i++) {
            for (ProductModel.AttributesEntity.AttributeMembersEntity entity : mUiData.getAdapters().get(i).getAttributeMembersEntities()) {
                // 处理没有数据和没有库存(检测单个)
                if (mUiData.getResult().get(entity.getAttributeMemberId() + "") == null || mUiData.getResult().get(entity.getAttributeMemberId() + "").getStock() <= 0) {
                    entity.setStatus(ProductModel.AttributeMemberStatus.UNCHECKABLE);
                } else if (entity.equals(mUiData.getAdapters().get(i).getCurrentSelectedItem())) {
                    entity.setStatus(ProductModel.AttributeMemberStatus.CHECKED);
                } else {
                    entity.setStatus(ProductModel.AttributeMemberStatus.CHECKABLE);
                }
                // 冒泡排序
                List<ProductModel.AttributesEntity.AttributeMembersEntity> cacheSelected = new ArrayList<>();
                cacheSelected.add(entity);
                cacheSelected.addAll(mUiData.getSelectedEntities());
                for (int j = 0; j < cacheSelected.size() - 1; j++) {
                    for (int k = 0; k < cacheSelected.size() - 1 - j; k++) {
                        ProductModel.AttributesEntity.AttributeMembersEntity cacheEntity;
                        if (cacheSelected.get(k).getAttributeGroupId() > cacheSelected.get(k + 1).getAttributeGroupId()) {
                            //交换数据
                            cacheEntity = cacheSelected.get(k);
                            cacheSelected.set(k, cacheSelected.get(k + 1));
                            cacheSelected.set(k + 1, cacheEntity);
                        }
                    }
                }
                for (int j = 0; j < cacheSelected.size() - 1; j++) {
                    for (int k = 0; k < cacheSelected.size() - 1 - j; k++) {
                        if (cacheSelected.get(k).getAttributeGroupId() == cacheSelected.get(k + 1).getAttributeGroupId()) {
                            cacheSelected.remove(k + 1);
                        }
                    }
                }
                StringBuffer buffer = new StringBuffer();
                for (ProductModel.AttributesEntity.AttributeMembersEntity selectedEntity : cacheSelected) {
                    buffer.append(selectedEntity.getAttributeMemberId() + ",");
                }
//                Log.e(TAG, "key = " + buffer.substring(0, buffer.length() - 1));
                //TODO 检查数据
                if (mUiData.getResult().get(buffer.substring(0, buffer.length() - 1)) != null && mUiData.getResult().get(buffer.substring(0, buffer.length() - 1)).getStock() > 0) {
                    entity.setStatus(entity.getStatus() == ProductModel.AttributeMemberStatus.CHECKED ? ProductModel.AttributeMemberStatus.CHECKED :
                            ProductModel.AttributeMemberStatus.CHECKABLE);
                } else {
                    entity.setStatus(ProductModel.AttributeMemberStatus.UNCHECKABLE);
                }
            }
            mUiData.getAdapters().get(i).notifyDataSetChanged();
        }

        //规格提示
        StringBuilder tips = new StringBuilder();
        //specCmob
        StringBuilder specId = new StringBuilder();
        if (mUiData.getSelectedMap().size() == mUiData.getGroupNameList().size()) {
            //所有规格选择完了，展示价格 和 库存
            for (String groupName : mUiData.getGroupNameList()) {
                for (String k : mUiData.getSelectedMap().keySet()) {
                    if (groupName.equals(k)) {
                        tips.append(mUiData.getSelectedMap().get(k).getName()).append(" ");
                        specId.append(mUiData.getSelectedMap().get(k).getAttributeMemberId()).append(",");
                    }
                }
            }
            tips.insert(0, "已选 ");
            Log.d(TAG, "specId  " + specId);

            ObserverHolder.getInstance().notifyObservers(specId.toString(), ObserverEventCode.SKU_GETSPECID);
        } else {
            for (String groupName : mUiData.getGroupNameList()) {
                if (!mUiData.getSelectedMap().keySet().contains(groupName))
                    tips.append(groupName).append(" ");
            }
            tips.insert(0, "请选择 ");
            ObserverHolder.getInstance().notifyObservers("null", ObserverEventCode.SKU_GETSPECID);
        }

        ObserverHolder.getInstance().notifyObservers(tips.toString(), ObserverEventCode.SKU_TIPS);


    }
}
