package com.xj.glmall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 限时购表
 * 
 * @author yu
 * @email ${email}
 * @date 2020-06-23 23:32:29
 */
@Data
@TableName("sms_flash_promotion")
public class FlashPromotionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private String title;
	/**
	 * 开始日期
	 */
	private Date startDate;
	/**
	 * 结束日期
	 */
	private Date endDate;
	/**
	 * 上下线状态
	 */
	private Integer status;
	/**
	 * 秒杀时间段名称
	 */
	private Date createTime;

}
