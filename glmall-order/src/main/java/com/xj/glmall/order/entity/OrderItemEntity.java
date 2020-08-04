package com.xj.glmall.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 订单中所包含的商品
 * 
 * @author yu
 * @email ${email}
 * @date 2020-06-24 00:05:20
 */
@Data
@TableName("oms_order_item")
public class OrderItemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 订单id
	 */
	private Long orderId;
	/**
	 * 订单编号
	 */
	private String orderSn;
	/**
	 * 
	 */
	private Long productId;
	/**
	 * 
	 */
	private String productPic;
	/**
	 * 
	 */
	private String productName;
	/**
	 * 
	 */
	private String productBrand;
	/**
	 * 
	 */
	private String productSn;
	/**
	 * 销售价格
	 */
	private BigDecimal productPrice;
	/**
	 * 购买数量
	 */
	private Integer productQuantity;
	/**
	 * 商品sku编号
	 */
	private Long productSkuId;
	/**
	 * 商品sku条码
	 */
	private String productSkuCode;
	/**
	 * 商品分类id
	 */
	private Long productCategoryId;
	/**
	 * 商品的销售属性
	 */
	private String sp1;
	/**
	 * 
	 */
	private String sp2;
	/**
	 * 
	 */
	private String sp3;
	/**
	 * 商品促销名称
	 */
	private String promotionName;
	/**
	 * 商品促销分解金额
	 */
	private BigDecimal promotionAmount;
	/**
	 * 优惠券优惠分解金额
	 */
	private BigDecimal couponAmount;
	/**
	 * 积分优惠分解金额
	 */
	private BigDecimal integrationAmount;
	/**
	 * 该商品经过优惠后的分解金额
	 */
	private BigDecimal realAmount;
	/**
	 * 
	 */
	private Integer giftIntegration;
	/**
	 * 
	 */
	private Integer giftGrowth;
	/**
	 * 商品销售属性:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]
	 */
	private String productAttr;

}
