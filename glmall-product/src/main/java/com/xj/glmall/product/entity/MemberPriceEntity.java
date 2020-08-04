package com.xj.glmall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商品会员价格表
 * 
 * @author yu
 * @email ${email}
 * @date 2020-07-02 22:57:14
 */
@Data
@TableName("pms_member_price")
public class MemberPriceEntity implements Serializable {
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
	private Long memberLevelId;
	/**
	 * 会员价格
	 */
	private BigDecimal memberPrice;
	/**
	 * 
	 */
	private String memberLevelName;

}
