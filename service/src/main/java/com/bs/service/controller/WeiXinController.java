package com.bs.service.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bs.common.utils.AesUtil;
import com.bs.common.utils.HttpRequest;
import com.bs.common.utils.R;
import com.bs.service.entity.Customer;
import com.bs.service.entity.QuestionGroup;
import com.bs.service.service.ICustomerService;
import com.bs.service.service.IQuestionGroupService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/service/weixin")
@CrossOrigin
public class WeiXinController {

    @Value("${wx.appid}")
    private String appId;

    @Value("${wx.appsecret}")
    private String appSecret;

    @Autowired
    private ICustomerService customerService;

    @GetMapping("testWx")
    public R testWx() {
        return R.ok().data("appId", appId).data("appSecret",appSecret);
    }

    @ApiOperation("获取微信小程序的openid")
    @GetMapping("getOpenid")
    public R getOpenid(String code, String encryptedData, String iv ) throws Exception{

        //登录凭证不能为空
        if (code == null || code.length() == 0) {
            return R.error().message("code 不能为空");
        }
        //小程序唯一标识   (在微信小程序管理后台获取)
        String wxspAppid = appId;
        //小程序的 app secret (在微信小程序管理后台获取)
        String wxspSecret = appSecret;
        //授权（必填）
        String grant_type = "authorization_code";
        //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
        //请求参数
        String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type=" + grant_type;
        //发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        //解析相应内容（转换成json对象）
        JSONObject json = JSONObject.parseObject(sr);
        //获取会话密钥（session_key）
        String session_key = json.get("session_key").toString();
        //用户的唯一标识（openid）
        String openid = (String) json.get("openid");
        System.out.println("openid:" + openid);
        //////////////// 2、对encryptedData加密数据进行AES解密 ////////////////
        try {
            String result = AesUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
            if (null != result && result.length() > 0) {

                JSONObject userInfoJSON = JSONObject.parseObject(result);
                String openId = (String) userInfoJSON.get("openId");
                QueryWrapper<Customer> wrapper = new QueryWrapper<>();
                wrapper.eq("open_id",openId);
                Customer customer = customerService.getOne(wrapper);
                if (customer == null){
                    customer = new Customer();
                }
                customer.setOpenId((String) userInfoJSON.get("openId"));
                customer.setNickName((String) userInfoJSON.get("nickName"));
                customer.setGender((Integer) userInfoJSON.get("gender"));
                customer.setCity((String) userInfoJSON.get("city"));
                customer.setProvince((String) userInfoJSON.get("province"));
                customer.setCountry((String) userInfoJSON.get("country"));
                customer.setAvatarUrl((String) userInfoJSON.get("avatarUrl"));
                boolean save = customerService.saveOrUpdate(customer);
                return save ? R.ok().data("customerId",customer.getId()) : R.error();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return R.error().message("解密失败");
    }
}
