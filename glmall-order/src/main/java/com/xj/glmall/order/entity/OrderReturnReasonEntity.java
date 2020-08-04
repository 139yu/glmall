package com.xj.glmall.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 退货原因表
 * 
 * @author yu
 * @email ${email}
 * @date 2020-06-24 00:05:19
 */
@Data
@TableName("oms_order_return_reason")
public class OrderReturnReasonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 退货类型
	 */
	private String name;
	/**
	 * 
	 */
	private Integer sort;
	/**
	 * 状态：0->不启用；1->启用
	 */
	private Integer status;
	/**
	 * 添加时间
	 */
	private Date createTime;

}
