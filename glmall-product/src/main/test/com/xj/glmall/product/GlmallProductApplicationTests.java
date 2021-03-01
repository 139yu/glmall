package com.xj.glmall.product;

import com.alibaba.fastjson.JSON;
import com.xj.glmall.product.service.AttrGroupService;
import com.xj.glmall.product.vo.SpuItemBaseAttrVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GlmallProductApplicationTests {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private AttrGroupService attrGroupService;

    @Test
    public void testRedisTemplate(){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set("test","hello");
        System.out.println(operations.get("test"));
    }

    @Test
    public void testAttrGroupService(){
        List<SpuItemBaseAttrVo> spuItemBaseAttrVos = attrGroupService.listAttrGroupWithAttrsBySpuIdAndCatalogId(9L, 225L);
        System.out.println(JSON.toJSONString(spuItemBaseAttrVos));

    }
}
