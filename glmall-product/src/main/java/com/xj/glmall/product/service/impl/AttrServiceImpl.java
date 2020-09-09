package com.xj.glmall.product.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xj.glmall.common.constant.ProductConstant;
import com.xj.glmall.product.dao.AttrAttrgroupRelationDao;
import com.xj.glmall.product.dao.AttrGroupDao;
import com.xj.glmall.product.dao.CategoryDao;
import com.xj.glmall.product.entity.AttrAttrgroupRelationEntity;
import com.xj.glmall.product.entity.AttrGroupEntity;
import com.xj.glmall.product.entity.CategoryEntity;
import com.xj.glmall.product.service.CategoryService;
import com.xj.glmall.product.vo.AttrRespVo;
import com.xj.glmall.product.vo.AttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.product.dao.AttrDao;
import com.xj.glmall.product.entity.AttrEntity;
import com.xj.glmall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("attrService")
@Transactional
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private AttrDao attrDao;

    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Autowired
    private AttrGroupDao attrGroupDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId,String type) {
        IPage<AttrEntity> page = null;
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("attr_type","base".equalsIgnoreCase(type) ? ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() : ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());
        if (!StringUtils.isEmpty((String) params.get("key"))){
            String key = (String) params.get("key");
            wrapper.and(obj -> {
                obj.eq("attr_id",key).or().like("attr_name",key).or().like("vaule_select",key);
            });
        }
        if (catelogId == 0) {
            page = this.page(new Query<AttrEntity>().getPage(params),wrapper);
        }else {
            wrapper.eq("catelog_id",catelogId);

            page = this.page(new Query<AttrEntity>().getPage(params),wrapper);
        }
        return new PageUtils(page);
    }

    @Override
    public boolean save(AttrEntity entity) {
        System.out.println(entity);
        attrDao.insert(entity);
        return true;
    }

    @Override
    public boolean saveAttr(AttrVo attrVo) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrVo,attrEntity);
        this.save(attrEntity);
        if (attrVo.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() && attrVo.getAttrGroupId() != null) {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrGroupId(attrVo.getAttrGroupId());
            attrAttrgroupRelationEntity.setAttrId(attrEntity.getAttrId());
            attrAttrgroupRelationDao.insert(attrAttrgroupRelationEntity);
        }
        return true;
    }

    @Override
    public PageUtils getBaseAttrList(Map<String, Object> params, Long catelogId,String type) {
        IPage<AttrEntity> page = null;
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("attr_type","base".equalsIgnoreCase(type) ? ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() : ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());
        String key = (String) params.get("key");
        if (catelogId != 0) {
            wrapper.eq("catelog_id",catelogId);
        }
        if (!StringUtils.isEmpty(key)) {
            wrapper.and(obj -> {
                obj.eq("attr_id",key).or().like("attr_name",key).or().like("value_select",key);
            });
        }
        page = this.page(new Query<AttrEntity>().getPage(params),wrapper);
        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> records = page.getRecords();
        List<AttrRespVo> attrRespVoList = records.stream().map(attrEntity -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity,attrRespVo);
            if ("base".equalsIgnoreCase(type)) {
                //设置分组名
                AttrAttrgroupRelationEntity relationEntity = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrRespVo.getAttrId()));
                if (relationEntity != null && relationEntity.getAttrGroupId() != null) {
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(relationEntity.getAttrGroupId());
                    attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }
            //设置分类名
            CategoryEntity categoryEntity = categoryDao.selectById(attrRespVo.getCatelogId());
            attrRespVo.setCatelogName(categoryEntity.getName());
            return attrRespVo;
        }).collect(Collectors.toList());
        pageUtils.setList(attrRespVoList);
        return pageUtils;
    }

    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        AttrEntity attrEntity = attrDao.selectById(attrId);
        AttrRespVo attrRespVo = new AttrRespVo();
        BeanUtils.copyProperties(attrEntity,attrRespVo);
        AttrGroupEntity attrGroup = getAttrGroup(attrId);
        attrRespVo.setGroupName(attrGroup.getAttrGroupName());
        attrRespVo.setAttrGroupId(attrGroup.getAttrGroupId());
        Long[] catelogPath = categoryService.findCatelogPath(attrRespVo.getCatelogId());
        attrRespVo.setCatelogPath(catelogPath);
        return attrRespVo;
    }

    @Override
    public boolean updateAttr(AttrVo attrVo) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrVo,attrEntity);
        attrDao.updateById(attrEntity);
        AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
        relationEntity.setAttrId(attrVo.getAttrId());
        if (attrVo.getAttrGroupId() != null) {
            relationEntity.setAttrGroupId(attrVo.getAttrGroupId());
            Integer count = attrAttrgroupRelationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrVo.getAttrId()));
            if (count > 0) {
                attrAttrgroupRelationDao.update(relationEntity,new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",attrVo.getAttrId()));
            }else {
                attrAttrgroupRelationDao.insert(relationEntity);
            }
        }
        return true;
    }

    @Override
    public List<AttrEntity> getRelations(Long attrgroupId) {
        List<AttrAttrgroupRelationEntity> relationEntities = attrAttrgroupRelationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_Id", attrgroupId));
        List<Long> attrIds = relationEntities.stream().map(item -> {
            return item.getAttrId();
        }).collect(Collectors.toList());
        if (attrIds == null || attrIds.size() == 0) {
            return null;
        }
        return (List<AttrEntity>) this.listByIds(attrIds);
    }

    private AttrGroupEntity getAttrGroup(Long attrId) {
        AttrGroupEntity attrGroupEntity = new AttrGroupEntity();
        AttrAttrgroupRelationEntity relationEntity = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId));
        if (relationEntity != null) {
            attrGroupEntity = attrGroupDao.selectById(relationEntity.getAttrGroupId());
        }

        return attrGroupEntity;
    }

    @Override
    public PageUtils getAttrNoRelation(Map<String, Object> params, Long attrgroupId) {
        AttrGroupEntity attrGroup = attrGroupDao.selectById(attrgroupId);
        Long catelogId = attrGroup.getCatelogId();
        List<AttrGroupEntity> groupEntities = attrGroupDao.selectList(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        List<Long> attrGroupIds = groupEntities.stream().map(item -> {
            return item.getAttrGroupId();
        }).collect(Collectors.toList());
        List<AttrAttrgroupRelationEntity> relationEntities = attrAttrgroupRelationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", attrGroupIds));
        List<Long> attIds = relationEntities.stream().map(item -> {
            return item.getAttrId();
        }).collect(Collectors.toList());
        String key = (String) params.get("key");
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("catelog_id",catelogId).notIn("attr_id",attIds).eq("attr_type",ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());
        if (attIds != null && attIds.size() > 0) {
            wrapper.notIn("attr_id",attIds);
        }
        if (!StringUtils.isEmpty(key)) {
            wrapper.and(obj -> {
                obj.eq("attr_id",key).or().like("attr_name",key).or().like("value_select",key);
            });
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }
}
