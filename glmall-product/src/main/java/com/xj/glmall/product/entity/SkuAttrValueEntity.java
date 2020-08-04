package com.xj.glmall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * sku平台属性值关联表
 * 
 * @author yu
 * @email ${email}
 * @date 2020-07-02 22:57:13
 */
@Data
@TableName("pms_sku_attr_value")
public class SkuAttrValueEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	private Long id;
	/**
	 * 属性id（冗余)
	 */
	private Long attrId;
	/**
	 * 属性值id
	 */
	private Long valueId;
	/**
	 * skuid
	 */
	private Long skuId;

}
