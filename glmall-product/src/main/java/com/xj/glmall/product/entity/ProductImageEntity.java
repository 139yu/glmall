package com.xj.glmall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商品图片表
 * 
 * @author yu
 * @email ${email}
 * @date 2020-07-02 22:57:13
 */
@Data
@TableName("pms_product_image")
public class ProductImageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	private Long id;
	/**
	 * 商品id
	 */
	private Long productId;
	/**
	 * 图片名称
	 */
	private String imgName;
	/**
	 * 图片路径
	 */
	private String imgUrl;

}
