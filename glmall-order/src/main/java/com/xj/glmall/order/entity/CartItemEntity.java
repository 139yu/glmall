package com.xj.glmall.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 购物车表
 * 
 * @author yu
 * @email ${email}
 * @date 2020-06-24 00:05:20
 */
@Data
@TableName("oms_cart_item")
public class CartItemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private Long productId;
	/**
	 * 
	 */
	private Long productSkuId;
	/**
	 * 
	 */
	private Long memberId;
	/**
	 * 购买数量
	 */
	private Integer quantity;
	/**
	 * 添加到购物车的价格
	 */
	private BigDecimal price;
	/**
	 * 销售属性1
	 */
	private String sp1;
	/**
	 * 销售属性2
	 */
	private String sp2;
	/**
	 * 销售属性3
	 */
	private String sp3;
	/**
	 * 商品主图
	 */
	private String productPic;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 商品副标题（卖点）
	 */
	private String productSubTitle;
	/**
	 * 商品sku条码
	 */
	private String productSkuCode;
	/**
	 * 会员昵称
	 */
	private String memberNickname;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 修改时间
	 */
	private Date modifyDate;
	/**
	 * 是否删除
	 */
	private Integer deleteStatus;
	/**
	 * 商品分类
	 */
	private Long productCategoryId;
	/**
	 * 
	 */
	private String productBrand;
	/**
	 * 
	 */
	private String productSn;
	/**
	 * 商品销售属性:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]
	 */
	private String productAttr;

}
