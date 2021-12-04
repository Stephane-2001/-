package com.leeyen.crowd.controller;

import com.leeyen.crowd.api.MysqlRemoteService;
import com.leeyen.crowd.api.RedisRemoteService;
import com.leeyen.crowd.config.ShortMessageProperties;
import com.leeyen.crowd.constant.CrowdConstant;
import com.leeyen.crowd.entity.po.MemberPO;
import com.leeyen.crowd.entity.vo.MemberLoginVO;
import com.leeyen.crowd.entity.vo.MemberVO;
import com.leeyen.crowd.util.CrowdUtil;
import com.leeyen.crowd.util.ResultEntity;
import com.netflix.discovery.converters.Auto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Controller
public class MemberController {

    @Autowired
    private ShortMessageProperties messageProperties;

    @Autowired
    private RedisRemoteService redisRemoteService;

    @Autowired
    private MysqlRemoteService mysqlRemoteService;


    Logger logger = LoggerFactory.getLogger(MemberController.class);

    /***
     * 实现注册时发送验证码
     * @param phoneNum 电话号码
     * @return
     */
    @ResponseBody
    @RequestMapping("/auth/member/send/short/message.json")
    public ResultEntity<String> sendMessage(
            @RequestParam("phoneNum") String phoneNum
    ) {
        // 1.发送验证码到手机
        ResultEntity<String> sendResultEntity = CrowdUtil.sendShortMessage(
                messageProperties.getHost(),
                messageProperties.getPath(),
                messageProperties.getMethod(), phoneNum,
                messageProperties.getAppcode(),
                messageProperties.getTemplate_id());
        // 2.判断验证码发送结果
        if (ResultEntity.SUCCESS.equals(sendResultEntity.getResult())) {
            // 3.如果发送成功则存入redis数据库
            String value = sendResultEntity.getData();
            String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;

            ResultEntity<String> redisResultEntity =
                    redisRemoteService.setRedisKeyValueWithTimeoutRemote(key, value, 5, TimeUnit.MINUTES);

            if (ResultEntity.SUCCESS.equals(redisResultEntity.getResult())) {

                return ResultEntity.successWithoutData();
            } else {

                return redisResultEntity;
            }
        } else {

            return sendResultEntity;
        }

    }

    /***
     * 实现注册操作
     * @param memberVO 页面传来的用户数据
     * @param modelMap
     * @return
     */
    @RequestMapping("/auth/member/do/register")
    public String register(MemberVO memberVO, ModelMap modelMap){
        // 1.读取手机号码
        String phoneNum = memberVO.getPhoneNum();
        // 2.得到redis中的key
        String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;
        // 3.从redis读取对应value
        ResultEntity<String> resultEntity = redisRemoteService.getRedisValueByKeyRemote(key);
        // 4.检查查询操作是否有效
        String result = resultEntity.getResult();
        String value = resultEntity.getData();

        if (ResultEntity.FAILED.equals(result)){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, resultEntity.getMessage());
            return "member-reg";
        }

        if (value == null){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_NOT_EXISTS);
            return "member-reg";
        }

        // 5.如果查询到value则比较表单的验证码
        String formcode = memberVO.getCode();

        if (!Objects.equals(formcode, value)){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_INVALID);
            return "member-reg";
        }

        // 6.验证码一致则从redis删除
        redisRemoteService.removeRedisKeyByKeyRemote(key);

        // 7.密码的加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String userpswd = memberVO.getUserpswd();

        String encode = passwordEncoder.encode(userpswd);
        memberVO.setUserpswd(encode);
        // 8.执行保存
        // 创建一个member对象
        MemberPO memberPO = new MemberPO();
        // 将vo对象复制到po种
        BeanUtils.copyProperties(memberVO, memberPO);
        // 调用远程方法保存
        ResultEntity<String> saveMemberEntity = mysqlRemoteService.saveMember(memberPO);

        if (ResultEntity.FAILED.equals(saveMemberEntity.getResult())){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, saveMemberEntity.getMessage());
            return "member-reg";
        }

        // 避免浏览器重新刷新 导致重新执行注册
        return "redirect:/auth/member/to/login/page.html";
    }

    @RequestMapping("/auth/member/do/login")
    public String doLogin(
            @RequestParam("loginacct") String loginacct,
            @RequestParam("userpswd") String userpswd,
            ModelMap modelMap,
            HttpSession session
    ){
        ResultEntity<MemberPO> resultEntity = mysqlRemoteService.getMemberPOByLoginAcctRemote(loginacct);

        // 验证查询是否成功
        if (ResultEntity.FAILED.equals(resultEntity.getResult())){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, resultEntity.getMessage());
            return "member-login";
        }

        MemberPO memberPO = resultEntity.getData();
        if (memberPO == null){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member-login";
        }

        // 从数据库获得密码
        String userpswdDataBase = memberPO.getUserpswd();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 比较带盐值的密码
        boolean matches = passwordEncoder.matches(userpswd ,userpswdDataBase);

        if (!matches){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member-login";
        }

        // 成功后存入session中
        MemberLoginVO memberLoginVO = new MemberLoginVO(memberPO.getId(), memberPO.getUsername(), memberPO.getEmail());
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER, memberLoginVO);

        return "redirect:/auth/member/to/center/page";

    }

    @RequestMapping("/auth/member/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

}
