package com.xj.glmall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 优惠卷表
 * 
 * @author yu
 * @email ${email}
 * @date 2020-06-23 23:32:29
 */
@Data
@TableName("sms_coupon")
public class CouponEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 优惠卷类型；0->全场赠券；1->会员赠券；2->购物赠券；3->注册赠券
	 */
	private Integer type;
	/**
	 * 
	 */
	private String name;
	/**
	 * 使用平台：0->全部；1->移动；2->PC
	 */
	private Integer platform;
	/**
	 * 数量
	 */
	private Integer count;
	/**
	 * 金额
	 */
	private BigDecimal amount;
	/**
	 * 每人限领张数
	 */
	private Integer perLimit;
	/**
	 * 使用门槛；0表示无门槛
	 */
	private BigDecimal minPoint;
	/**
	 * 
	 */
	private Date startTime;
	/**
	 * 
	 */
	private Date endTime;
	/**
	 * 使用类型：0->全场通用；1->指定分类；2->指定商品
	 */
	private Integer useType;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 发行数量
	 */
	private Integer publishCount;
	/**
	 * 已使用数量
	 */
	private Integer useCount;
	/**
	 * 领取数量
	 */
	private Integer receiveCount;
	/**
	 * 可以领取的日期
	 */
	private Date enableTime;
	/**
	 * 优惠码
	 */
	private String code;
	/**
	 * 可领取的会员类型：0->无限时
	 */
	private Integer memberLevel;

}
