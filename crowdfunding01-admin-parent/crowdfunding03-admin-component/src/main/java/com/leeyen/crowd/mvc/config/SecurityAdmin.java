package com.leeyen.crowd.mvc.config;

import com.leeyen.crowd.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/***
 * 为了能够获取到原始的admin对象 创建这个类方便于admin的获取
 */
public class SecurityAdmin extends User {
    private static final long serialVersionUID = 1L;
    private Admin originalAdmin;

    public Admin getOriginalAdmin() {
        return originalAdmin;
    }

    public SecurityAdmin(
            //  传入原始的admin对象
            Admin originalAdmin,
            // 传入权限的信息集合
            List<GrantedAuthority> authorities){
        // 调用父类的构造器
        super(originalAdmin.getLoginAcct(), originalAdmin.getUserPswd(), authorities);

        this.originalAdmin = originalAdmin;

        // 将原始admin中的密码擦除
        this.originalAdmin.setUserPswd(null);
    }



}
