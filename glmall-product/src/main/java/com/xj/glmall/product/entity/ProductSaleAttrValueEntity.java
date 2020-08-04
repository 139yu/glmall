package com.xj.glmall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * spu销售属性值
 * 
 * @author yu
 * @email ${email}
 * @date 2020-07-02 22:57:13
 */
@Data
@TableName("pms_product_sale_attr_value")
public class ProductSaleAttrValueEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 商品id
	 */
	private Long productId;
	/**
	 * 销售属性id
	 */
	private Long saleAttrId;
	/**
	 * 销售属性值名称
	 */
	private String saleAttrValueName;

}
