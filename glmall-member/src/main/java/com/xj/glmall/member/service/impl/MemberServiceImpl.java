package com.xj.glmall.member.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xj.glmall.common.utils.HttpUtils;
import com.xj.glmall.member.dao.MemberLevelDao;
import com.xj.glmall.member.entity.MemberLevelEntity;
import com.xj.glmall.member.exception.EmailExistException;
import com.xj.glmall.member.exception.UsernameExistException;
import com.xj.glmall.member.vo.SocialUser;
import com.xj.glmall.member.vo.UserLoginVo;
import com.xj.glmall.member.vo.UserRegisterVo;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.member.dao.MemberDao;
import com.xj.glmall.member.entity.MemberEntity;
import com.xj.glmall.member.service.MemberService;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Autowired
    private MemberLevelDao memberLevelDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void register(UserRegisterVo registerVo) throws UsernameExistException, EmailExistException {
        MemberEntity memberEntity = new MemberEntity();
        MemberLevelEntity defaultLevel = memberLevelDao.getDefaultLevel(1);
        memberEntity.setLevelId(defaultLevel.getId());
        /**
         * 利用异常机制，检查用户和邮箱是否唯一，若不唯一抛出异常，停止继续执行代码
         */
        checkUsernameUnion(registerVo.getUsername());
        checkEmailUnion(registerVo.getEmail());
        memberEntity.setUsername(registerVo.getUsername());
        memberEntity.setEmail(registerVo.getEmail());
        memberEntity.setPassword(bCryptPasswordEncoder.encode(registerVo.getPassword()));
        this.baseMapper.insert(memberEntity);
    }

    @Override
    public MemberEntity login(UserLoginVo loginVo) {
        MemberEntity memberEntity = this.baseMapper.selectOne(new QueryWrapper<MemberEntity>().eq("username", loginVo.getUsername()));
        if (memberEntity != null) {
            String password = memberEntity.getPassword();
            boolean matches = bCryptPasswordEncoder.matches(loginVo.getPassword(), password);
            if (matches) {
                return memberEntity;
            }
        }
        return null;
    }

    @Override
    public MemberEntity authLogin(SocialUser socialUser) throws Exception {
        MemberEntity memberEntity = this.baseMapper.selectOne(new QueryWrapper<MemberEntity>().eq("uid", socialUser.getUid()));
        //数据库没有该用户信息，为用户第一次登录，则从微博获取用户信息，添加到数据库
        if (memberEntity == null) {
            Map<String, String> map = new HashMap<>();
            map.put("access_token", socialUser.getAccess_token());
            map.put("uid", socialUser.getUid());
            HttpResponse response = HttpUtils.doGet("https://api.weibo.com", "/2/users/show.json", "get", new HashMap<>(), map);
            if (response.getStatusLine().getStatusCode() == 200) {
                String s = EntityUtils.toString(response.getEntity());
                JSONObject jsonObject = JSON.parseObject(s);
                MemberEntity insert = new MemberEntity();
                insert.setUid(socialUser.getUid());
                insert.setAccess_token(socialUser.getAccess_token());
                insert.setExpires_in(socialUser.getExpires_in());
                insert.setNickname(jsonObject.getString("name"));
                insert.setGender("m".equals(jsonObject.getString("gender")) ? 0 : 1);
                insert.setHeader(jsonObject.getString("profile_image_url"));
                this.baseMapper.insert(insert);
                return insert;
            }
        } else {
            MemberEntity update = new MemberEntity();
            update.setId(memberEntity.getId());
            update.setAccess_token(socialUser.getAccess_token());
            update.setExpires_in(socialUser.getExpires_in());
            this.baseMapper.updateById(update);
        }
        return memberEntity;
    }

    public void checkUsernameUnion(String username) throws UsernameExistException {
        Integer count = baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("username", username));
        if (count > 0) {
            throw new UsernameExistException();
        }
    }

    public void checkEmailUnion(String email) throws EmailExistException {
        Integer count = baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("email", email));
        if (count > 0) {
            throw new EmailExistException();
        }
    }

}