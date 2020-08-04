package com.xj.glmall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author yu
 * @email ${email}
 * @date 2020-07-02 22:57:14
 */
@Data
@TableName("pms_base_sale_attr")
public class BaseSaleAttrEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	private Long id;
	/**
	 * 销售属性名称
	 */
	private String name;

}
