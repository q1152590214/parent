package com.atguigu.educenter.service.impl;

import com.alibaba.nacos.common.util.Md5Utils;
import com.atguigu.JwtUtilt;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.exception.MyExcaption;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-07
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {


    @Resource
    RedisTemplate<String,String> stringStringRedisTemplate;

    /**
     * 根据手机号和密码登录
     * @param ucenterMember
     * @return
     */
    @Override
    public String login(UcenterMember ucenterMember) {

        String password = ucenterMember.getPassword();
        String mobile = ucenterMember.getMobile();
        //判断手机号和密码是否为空
        if (StringUtils.isEmpty(password)||StringUtils.isEmpty(mobile)){
            throw  new MyExcaption(20001,"密码和用户名不存在");
        }

        QueryWrapper<UcenterMember> ucenterMemberQueryWrapper = new QueryWrapper<>();
        ucenterMemberQueryWrapper.eq("mobile",mobile);
        UcenterMember memberUcenter= baseMapper.selectOne(ucenterMemberQueryWrapper);
        //判断是否存在用户

        if (memberUcenter==null){
            throw  new MyExcaption(20001,"密码和用户名不存在");
        }
        //判断密码是否一致
        if(!Md5Utils.getMD5(password,"utf-8").equals(memberUcenter.getPassword())){
            throw  new MyExcaption(20001,"密码或用户名不存在");
        }
        //判断用户状态
        if(memberUcenter.getIsDisabled()){
            throw  new MyExcaption(20001,"用户已被禁用");
        }
        //生产JWT token
        String jwtToken = JwtUtilt.getJwtToken(memberUcenter.getId(), memberUcenter.getNickname());

        return jwtToken;
    }


    /**
     * 注册功能
     * @param registerVo
     */
    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        if(StringUtils.isEmpty(code)||StringUtils.isEmpty(mobile)||StringUtils.isEmpty(nickname)||StringUtils.isEmpty(password)){
            throw  new MyExcaption(200001,"信息不能为空");
        }

        String redisCode = stringStringRedisTemplate.opsForValue().get(mobile);
        System.out.println(code);
        System.out.println(redisCode);

        if(!code.equals(redisCode)){
            throw  new MyExcaption(20001,"验证码错误");
        }
        QueryWrapper<UcenterMember> ucenterMemberQueryWrapper = new QueryWrapper<>();
        ucenterMemberQueryWrapper.eq("mobile",mobile);
        Integer integer = baseMapper.selectCount(ucenterMemberQueryWrapper);
        if(integer>0){
            throw new MyExcaption(20001,"用户已注册");
        }

        UcenterMember ucenterMember = new UcenterMember();
        BeanUtils.copyProperties(registerVo,ucenterMember);
        ucenterMember.setPassword(Md5Utils.getMD5(password,"utf-8"));
        ucenterMember.setIsDisabled(false);
        System.out.println(ucenterMember);
        int insert = baseMapper.insert(ucenterMember);
        System.out.println(insert);
    }
}
