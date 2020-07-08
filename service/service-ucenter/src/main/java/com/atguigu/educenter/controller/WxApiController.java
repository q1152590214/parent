package com.atguigu.educenter.controller;

import com.atguigu.JwtUtilt;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.educenter.utlis.ConstanWxUtils;
import com.atguigu.educenter.utlis.HttpClientUtils;
import com.atguigu.exception.MyExcaption;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.client.HttpClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@CrossOrigin
@RequestMapping("/api/ucenter/wx")
@Api(description = "微信登录模块")
public class WxApiController {


    @Resource
    private UcenterMemberService ucenterMemberService;

    @ApiOperation(value = "微信登录")
    @GetMapping("login")
    public String getWxCode(){
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        String wxOpenRedirectUrl = ConstanWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            wxOpenRedirectUrl= URLEncoder.encode(wxOpenRedirectUrl,"utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }


        String url = String.format(baseUrl, ConstanWxUtils.WX_OPEN_APP_ID,wxOpenRedirectUrl,"atguitu");

        return "redirect:"+url;
    }


    /**
     * 回调函数，进行微信登录获取信息的操作
     * @param code
     * @param state
     * @return
     */
    @GetMapping("callback")
    public String callback(String code, String state){

        //得到授权临时票据code
        System.out.println(code);
        System.out.println(state);

        //从redis中将state获取出来，和当前传入的state作比较
        //如果一致则放行，如果不一致则抛出异常：非法访问

        //向认证服务器发送请求换取access_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        String accessTokenUrl = String.format(baseAccessTokenUrl,
                ConstanWxUtils.WX_OPEN_APP_ID,
                ConstanWxUtils.WX_OPEN_APP_SECRET,
                code);

        String result = null;
        try {
            //通过httpCilent发送get请求 放回json
            result = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessToken=============" + result);
        } catch (Exception e) {
            throw new MyExcaption(20001, "获取access_token失败");
        }

        //解析json字符串
        Gson gson = new Gson();
        HashMap map = gson.fromJson(result, HashMap.class);
        //获取access_token 和 openid
        String accessToken = (String)map.get("access_token");
        String openid = (String)map.get("openid");

        //查询数据库当前用用户是否曾经使用过微信登录
      UcenterMember ucenterMember = ucenterMemberService.getByOpenid(openid);
        if(ucenterMember == null){
            System.out.println("新用户注册");
            //访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
            String resultUserInfo = null;
            try {
                resultUserInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println("resultUserInfo==========" + resultUserInfo);
            } catch (Exception e) {
                throw new MyExcaption(20001, "获取用户信息失败");
            }

            //解析json
            HashMap<String, Object> mapUserInfo = gson.fromJson(resultUserInfo, HashMap.class);
            //获取名称
            String nickname = (String)mapUserInfo.get("nickname");
            //获取头像地址
            String headimgurl = (String)mapUserInfo.get("headimgurl");

            //向数据库中插入一条记录
            ucenterMember = new UcenterMember();
            ucenterMember.setNickname(nickname);
            ucenterMember.setOpenid(openid);
            ucenterMember.setAvatar(headimgurl);
            ucenterMemberService.save(ucenterMember);
        }

        String jwtToken = JwtUtilt.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
        //TODO 登录
        //跳转登录成功的地址
        return "redirect:http://localhost:3000?token="+jwtToken;
    }


}
