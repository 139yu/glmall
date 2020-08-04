package com.xj.glmall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 属性值表
 * 
 * @author yu
 * @email ${email}
 * @date 2020-07-02 22:57:14
 */
@Data
@TableName("pms_base_attr_value")
public class BaseAttrValueEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	private Long id;
	/**
	 * 属性值名称
	 */
	private String valueName;
	/**
	 * 属性id
	 */
	private Long attrId;
	/**
	 * 启用：1 停用：0 1
	 */
	private String isEnabled;

}
