package com.xj.glmall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 优惠券使用、领取历史表
 * 
 * @author yu
 * @email ${email}
 * @date 2020-06-23 23:32:29
 */
@Data
@TableName("sms_coupon_history")
public class CouponHistoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private Long couponId;
	/**
	 * 
	 */
	private Long memberId;
	/**
	 * 
	 */
	private String couponCode;
	/**
	 * 领取人昵称
	 */
	private String memberNickname;
	/**
	 * 获取类型：0->后台赠送；1->主动获取
	 */
	private Integer getType;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 使用状态：0->未使用；1->已使用；2->已过期
	 */
	private Integer useStatus;
	/**
	 * 使用时间
	 */
	private Date useTime;
	/**
	 * 订单编号
	 */
	private Long orderId;
	/**
	 * 订单号码
	 */
	private String orderSn;

}
