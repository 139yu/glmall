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
@TableName("wms_ware_sku")
public class WareSkuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	private Long id;
	/**
	 * skuid
	 */
	private Long skuId;
	/**
	 * 仓库id
	 */
	private Long warehouseId;
	/**
	 * 库存数
	 */
	private Integer stock;
	/**
	 * 存货名称
	 */
	private String stockName;
	/**
	 * 
	 */
	private Integer stockLocked;

}
