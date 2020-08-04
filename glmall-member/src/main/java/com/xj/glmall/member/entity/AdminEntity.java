package com.xj.glmall.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 后台用户表
 * 
 * @author yu
 * @email yu
 * @date 2020-06-24 20:01:37
 */
@Data
@TableName("ums_admin")
public class AdminEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private String username;
	/**
	 * 
	 */
	private String password;
	/**
	 * 头像
	 */
	private String icon;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 备注信息
	 */
	private String note;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 最后登录时间
	 */
	private Date loginTime;
	/**
	 * 帐号启用状态：0->禁用；1->启用
	 */
	private Integer status;

}
