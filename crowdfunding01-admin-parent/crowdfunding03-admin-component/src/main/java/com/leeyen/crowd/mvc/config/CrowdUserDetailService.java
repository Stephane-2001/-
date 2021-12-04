package com.leeyen.crowd.mvc.config;

import com.leeyen.crowd.entity.Admin;
import com.leeyen.crowd.entity.Role;
import com.leeyen.crowd.service.api.AdminService;
import com.leeyen.crowd.service.api.AuthService;
import com.leeyen.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CrowdUserDetailService implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String loginAcct) throws UsernameNotFoundException {
        // 1.根据账号查询admin对象
        Admin admin = adminService.getAdminByLoginAcct(loginAcct);

        // 2.获取adminId
        Integer adminId = admin.getId();

        // 3.查询角色信息
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);

        // 4.查询权限信息
        List<String> authNameList = authService.selectAssignedAuthNameByAdminId(adminId);

        // 5.创建集合对象用来存储gtantedauthority
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 6.存入角色信息
        for (Role  role: assignedRoleList){

            String roleName = "ROLE_" + role.getName();

            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleName);

            authorities.add(simpleGrantedAuthority);
        }

        // 7.遍历权限信息
        for (String authName : authNameList){
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authName);

            authorities.add(simpleGrantedAuthority);
        }

        // 8.封装进入security对象
        SecurityAdmin securityAdmin = new SecurityAdmin(admin, authorities);

        return securityAdmin;
    }
}
