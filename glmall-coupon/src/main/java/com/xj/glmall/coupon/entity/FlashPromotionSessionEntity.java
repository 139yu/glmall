package com.xj.glmall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import lombok.Data;

/**
 * 限时购场次表
 * 
 * @author yu
 * @email ${email}
 * @date 2020-06-23 23:32:29
 */
@Data
@TableName("sms_flash_promotion_session")
public class FlashPromotionSessionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	private Long id;
	/**
	 * 场次名称
	 */
	private String name;
	/**
	 * 每日开始时间
	 */
	private Time startTime;
	/**
	 * 每日结束时间
	 */
	private Time endTime;
	/**
	 * 启用状态：0->不启用；1->启用
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
