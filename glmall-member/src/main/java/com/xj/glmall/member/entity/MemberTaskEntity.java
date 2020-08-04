package com.xj.glmall.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 会员任务表
 * 
 * @author yu
 * @email yu
 * @date 2020-06-24 20:01:36
 */
@Data
@TableName("ums_member_task")
public class MemberTaskEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private String name;
	/**
	 * 赠送成长值
	 */
	private Integer growth;
	/**
	 * 赠送积分
	 */
	private Integer intergration;
	/**
	 * 任务类型：0->新手任务；1->日常任务
	 */
	private Integer type;

}
