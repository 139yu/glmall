package com.xj.glmall.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 后台用户角色表
 * 
 * @author yu
 * @email yu
 * @date 2020-06-24 20:01:37
 */
@Data
@TableName("ums_role")
public class RoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 后台用户数量
	 */
	private Integer adminCount;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 启用状态：0->禁用；1->启用
	 */
	private Integer status;
	/**
	 * 
	 */
	private Integer sort;

}
