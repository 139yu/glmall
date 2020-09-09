package com.xj.glmall.product.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xj.glmall.product.dao.AttrAttrgroupRelationDao;
import com.xj.glmall.product.dao.AttrDao;
import com.xj.glmall.product.entity.AttrAttrgroupRelationEntity;
import com.xj.glmall.product.entity.AttrEntity;
import com.xj.glmall.product.service.AttrService;
import com.xj.glmall.product.vo.AttrGroupRelationVo;
import com.xj.glmall.product.vo.AttrGroupVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.product.dao.AttrGroupDao;
import com.xj.glmall.product.entity.AttrGroupEntity;
import com.xj.glmall.product.service.AttrGroupService;
import org.springframework.util.StringUtils;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {


    @Autowired
    private AttrGroupDao attrGroupDao;
    @Autowired
    private AttrService attrService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long categoryId) {
        IPage<AttrGroupEntity> page = null;
        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and(obj -> {
                obj.eq("attr_group_id",key).or().like("attr_group_name",key).or().like("descript",key);
            });
        }
        if(categoryId == 0) {
            page = this.page(
                    new Query<AttrGroupEntity>().getPage(params),
                    wrapper
            );
        }else {
            wrapper.eq("catelog_id",categoryId);
            page = this.page(new Query<AttrGroupEntity>().getPage(params), wrapper);
        }
        return new PageUtils(page);
    }

    @Override
    public void deleteRelation(AttrGroupRelationVo[] attrGroupRelationVos) {
        List<AttrAttrgroupRelationEntity> relationEntities = Arrays.asList(attrGroupRelationVos).stream().map(item -> {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(item.getAttrGroupId());
            relationEntity.setAttrId(item.getAttrId());
            return relationEntity;
        }).collect(Collectors.toList());
        this.baseMapper.deleteBatchRelation(relationEntities);

    }

    @Override
    public List<AttrGroupVo> getAttrGroupWithAttr(Long catelogId) {
        List<AttrGroupEntity> groupEntities = attrGroupDao.selectList(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        List<AttrGroupVo> collect = groupEntities.stream().map(item -> {
            AttrGroupVo attrGroupVo = new AttrGroupVo();
            BeanUtils.copyProperties(item, attrGroupVo);
            List<AttrEntity> relations = attrService.getRelations(item.getAttrGroupId());
            attrGroupVo.setAttrs(relations);
            return attrGroupVo;
        }).collect(Collectors.toList());
        return collect;
    }


}
