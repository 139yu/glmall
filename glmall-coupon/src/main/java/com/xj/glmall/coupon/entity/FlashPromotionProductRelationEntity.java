package com.xj.glmall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商品限时购与商品关系表
 * 
 * @author yu
 * @email ${email}
 * @date 2020-06-23 23:32:29
 */
@Data
@TableName("sms_flash_promotion_product_relation")
public class FlashPromotionProductRelationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private Long flashPromotionId;
	/**
	 * 编号
	 */
	private Long flashPromotionSessionId;
	/**
	 * 
	 */
	private Long productId;
	/**
	 * 限时购价格
	 */
	private BigDecimal flashPromotionPrice;
	/**
	 * 限时购数量
	 */
	private Integer flashPromotionCount;
	/**
	 * 每人限购数量
	 */
	private Integer flashPromotionLimit;
	/**
	 * 排序
	 */
	private Integer sort;

}
