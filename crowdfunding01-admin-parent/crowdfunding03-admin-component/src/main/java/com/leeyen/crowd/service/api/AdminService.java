package com.leeyen.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.leeyen.crowd.entity.Admin;

import java.util.List;

public interface AdminService {
    void saveAdmin(Admin admin);

    List<Admin> getAll();

    Admin getAdminByLoginAcct(String loginAcct, String userPswd);

    PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

    void remove(Integer adminId);

    Admin getAdminById(Integer adminId);

    void update(Admin admin);

    void saveAdminRoleRelatonship(Integer adminId, List<Integer> roleIdList);

    Admin getAdminByLoginAcct(String loginAcct);
}
