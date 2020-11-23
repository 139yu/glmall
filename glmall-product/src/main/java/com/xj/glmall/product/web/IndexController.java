package com.xj.glmall.product.web;

import com.xj.glmall.product.config.RedissonConfig;
import com.xj.glmall.product.entity.CategoryEntity;
import com.xj.glmall.product.service.CategoryService;
import com.xj.glmall.product.vo.Catalog2Vo;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class IndexController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedissonClient redisson;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping({"/","/index.html"})
    public String indexPage(Model model){
        List<CategoryEntity> categoryEntities = categoryService.getLevel1Category();
        model.addAttribute("categories",categoryEntities);
        return "index";
    }

    @ResponseBody
    @GetMapping("/index/json/catalog.json")
    public Map<String ,List<Catalog2Vo>> getCatalogJson(){
        Map<String, List<Catalog2Vo>> catalogMap = categoryService.getCatalogJson();
        return catalogMap;
    }

    @ResponseBody
    @GetMapping("/read")
    public String read(){
        RReadWriteLock lock = redisson.getReadWriteLock("read_write");
        RLock rLock = lock.readLock();
        rLock.lock();

        String test = null;
        try{
            System.out.println("get read lock");
            test = redisTemplate.opsForValue().get("test");
        }finally {
            System.out.println("release read lock");
            rLock.unlock();
            return test;
        }
    }

    @ResponseBody
    @GetMapping("/write")
    public String write(){
        RReadWriteLock lock = redisson.getReadWriteLock("read_write");
        RLock wLock = lock.writeLock();
        wLock.lock();

        String s = UUID.randomUUID().toString();
        try{
            System.out.println("get write lock");
            Thread.sleep(10000);
            redisTemplate.opsForValue().set("test",s);
        }finally {
            System.out.println("release write lock");
            wLock.unlock();
            return s;
        }
    }

    //信号量
    @ResponseBody
    @GetMapping("/park")
    public String park() throws InterruptedException {
        RSemaphore park = redisson.getSemaphore("park");
        boolean b = park.tryAcquire();
        if (b) {
            return "车已停好";
        }else {
            return "暂无车位";
        }

    }
    @ResponseBody
    @GetMapping("/go")
    public String go() throws InterruptedException {
        RSemaphore park = redisson.getSemaphore("park");
        park.release();

        return "空出一车位";
    }
}
