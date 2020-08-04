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
@TableName("pms_base_catalog2")
public class BaseCatalog2Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	private Integer id;
	/**
	 * 二级分类名称
	 */
	private String name;
	/**
	 * 一级分类编号
	 */
	private Integer catalog1Id;

}
