package com.leeyen.crowd.service.api;

import com.leeyen.crowd.entity.po.MemberPO;

public interface MemberService {
    MemberPO getMemberPOByLoginAcct(String loginacct);

    void saveMember(MemberPO memberPO);
}
