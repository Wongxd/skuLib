package io.github.wongxd.skulibray.specSelect.sku.skuLib.model;

/**
 * Sku
 * <p>
 * Sku基本模型数据
 */
public class BaseSkuModel {

    //base 属性
    private CharSequence price;//价格
    private int stock;//库存

    public BaseSkuModel() {
    }

    public BaseSkuModel(CharSequence price, int stock) {
        this.price = price;
        this.stock = stock;
    }

    public CharSequence getPrice() {
        return price;
    }

    public void setPrice(CharSequence price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
