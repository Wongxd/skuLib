package io.github.wongxd.skulibray.specSelect.sku;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.wongxd.skulibray.specSelect.sku.skuLib.model.BaseSkuModel;


public class UiData {

    /**
     * 当前选择的规格组合
     */

    Map<String, ProductModel.AttributesEntity.AttributeMembersEntity> selectedMap = new HashMap<>();

    public Map<String, ProductModel.AttributesEntity.AttributeMembersEntity> getSelectedMap() {
        return selectedMap;
    }

    public void setSelectedMap(Map<String, ProductModel.AttributesEntity.AttributeMembersEntity> selectedMap) {
        this.selectedMap = selectedMap;
    }

    /**
     * 所有规格 的 组名
     */
    List<String> groupNameList = new ArrayList<String>();

    public List<String> getGroupNameList() {
        return groupNameList;
    }

    public void setGroupNameList(List<String> groupNameList) {
        this.groupNameList = groupNameList;
    }


    // 保存多组adapter
    List<SkuAdapter> adapters = new ArrayList<>();

    //存放被选中的按钮对应的数据
    List<ProductModel.AttributesEntity.AttributeMembersEntity> selectedEntities = new ArrayList<>();

    //存放计算结果
    Map<String, BaseSkuModel> result;

    public List<SkuAdapter> getAdapters() {
        return adapters;
    }

    public void setAdapters(List<SkuAdapter> adapters) {
        this.adapters = adapters;
    }

    public Map<String, BaseSkuModel> getResult() {
        return result;
    }

    public void setResult(Map<String, BaseSkuModel> result) {
        this.result = result;
    }


    public List<ProductModel.AttributesEntity.AttributeMembersEntity> getSelectedEntities() {
        return selectedEntities;
    }

    public void setSelectedEntities(List<ProductModel.AttributesEntity.AttributeMembersEntity> selectedEntities) {
        this.selectedEntities = selectedEntities;
    }
}
