package com.xj.glmall.glmall.cart.vo;

import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

public class Cart {
    private List<CartItem> items;
    private Integer countNum;//商品数量
    private Integer countType;//商品类型数量
    private BigDecimal totalAmount;//商品总价
    private BigDecimal reduce = new BigDecimal(0);//减免价格

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public Integer getCountNum() {
        int amount = 0;
        if (!CollectionUtils.isEmpty(items)) {
            for (CartItem item : items) {
                Integer count = item.getCount();
                amount += count;
            }
        }
        return amount;
    }

    public Integer getCountType() {
        return this.items.size();
    }

    public BigDecimal getTotalAmount() {
        BigDecimal total = new BigDecimal(0);
        if(!CollectionUtils.isEmpty(this.items)){
            for (CartItem item : this.items) {
                if (item.getCheck()){
                    BigDecimal decimal = item.getTotalPrice();
                    total = total.add(decimal);
                }
            }
        }
        return total.subtract(this.reduce);
    }


    public BigDecimal getReduce() {
        return reduce;
    }

    public void setReduce(BigDecimal reduce) {
        this.reduce = reduce;
    }
}
