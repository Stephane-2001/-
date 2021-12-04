package com.leeyen.crowd.service.api;

import com.leeyen.crowd.entity.Auth;

import java.util.List;
import java.util.Map;

public interface AuthService {
    List<Auth> getAllAuth();

    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

    void saveRoleAuthRelathinship(Map<String, List<Integer>> map);

    List<String> selectAssignedAuthNameByAdminId(Integer adminId);
}
