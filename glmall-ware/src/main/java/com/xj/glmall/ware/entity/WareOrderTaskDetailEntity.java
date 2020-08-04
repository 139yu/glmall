package com.xj.glmall.ware.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author yu
 * @email yu
 * @date 2020-06-24 20:15:57
 */
@Data
@TableName("wms_ware_order_task_detail")
public class WareOrderTaskDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	private Long id;
	/**
	 * sku_id
	 */
	private Long skuId;
	/**
	 * sku名称
	 */
	private String skuName;
	/**
	 * 购买个数
	 */
	private Integer skuNums;
	/**
	 * 工作单编号
	 */
	private Long taskId;
	/**
	 * 
	 */
	private Integer skuNum;

}
