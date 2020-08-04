package com.xj.glmall.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 公司收发货地址表
 * 
 * @author yu
 * @email ${email}
 * @date 2020-06-24 00:05:20
 */
@Data
@TableName("oms_company_address")
public class CompanyAddressEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 地址名称
	 */
	private String addressName;
	/**
	 * 默认发货地址：0->否；1->是
	 */
	private Integer sendStatus;
	/**
	 * 是否默认收货地址：0->否；1->是
	 */
	private Integer receiveStatus;
	/**
	 * 收发货人姓名
	 */
	private String name;
	/**
	 * 收货人电话
	 */
	private String phone;
	/**
	 * 省/直辖市
	 */
	private String province;
	/**
	 * 市
	 */
	private String city;
	/**
	 * 区
	 */
	private String region;
	/**
	 * 详细地址
	 */
	private String detailAddress;

}
