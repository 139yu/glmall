package com.xj.glmall.ware.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 库存工作单表 库存工作单表
 * 
 * @author yu
 * @email yu
 * @date 2020-06-24 20:15:57
 */
@Data
@TableName("wms_ware_order_task")
public class WareOrderTaskEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	private Long id;
	/**
	 * 订单编号
	 */
	private Long orderId;
	/**
	 * 收货人
	 */
	private String consignee;
	/**
	 * 收货人电话
	 */
	private String consigneeTel;
	/**
	 * 送货地址
	 */
	private String deliveryAddress;
	/**
	 * 订单备注
	 */
	private String orderComment;
	/**
	 * 付款方式 1:在线付款 2:货到付款
	 */
	private String paymentWay;
	/**
	 * 
	 */
	private String taskStatus;
	/**
	 * 订单描述
	 */
	private String orderBody;
	/**
	 * 物流单号
	 */
	private String trackingNo;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 仓库编号
	 */
	private Long wareId;
	/**
	 * 工作单备注
	 */
	private String taskComment;

}
