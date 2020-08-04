package com.xj.glmall.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户和标签关系表
 * 
 * @author yu
 * @email yu
 * @date 2020-06-24 20:01:36
 */
@Data
@TableName("ums_member_member_tag_relation")
public class MemberMemberTagRelationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private Long memberId;
	/**
	 * 
	 */
	private Long tagId;

}
