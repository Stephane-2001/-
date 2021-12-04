package com.leeyen.crowd.controller;

import com.leeyen.crowd.constant.CrowdConstant;
import com.leeyen.crowd.service.api.MemberService;
import com.leeyen.crowd.entity.po.MemberPO;
import com.leeyen.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberProviderController {

    @Autowired
    private MemberService memberService;

    @RequestMapping("/get/memberpo/by/login/acct/remote")
    public ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct){

        try{
            // 1.调用本地service进行查询
            MemberPO memberPO = memberService.getMemberPOByLoginAcct(loginacct);

            // 2.返回正常的结果
            return ResultEntity.successWithData(memberPO);
        }catch (Exception e){
            e.printStackTrace();

            // 3.返回失败结果
            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("/save/member/remote")
    public ResultEntity<String> saveMember(@RequestBody MemberPO memberPO){
        try {
            memberService.saveMember(memberPO);
            return ResultEntity.successWithoutData();
        }catch (Exception e){
            if (e instanceof DuplicateKeyException){
                return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_ACCOUNT_ALREADY_IN_USE);
            }
            return ResultEntity.failed(e.getMessage());
        }
    }


}
