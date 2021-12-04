package com.leeyen.crowd.test;

import com.leeyen.crowd.entity.Admin;
import com.leeyen.crowd.entity.Role;
import com.leeyen.crowd.mapper.AdminMapper;
import com.leeyen.crowd.mapper.RoleMapper;
import com.leeyen.crowd.service.api.AdminService;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.lang.ref.PhantomReference;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-persist-mybatis.xml","classpath:spring-persist-tx.xml"})
public class CrowdTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void testRole(){
        for (int i = 0; i < 10; i++){
            roleMapper.insert(new Role(null, "name" + i));
        }
    }

    @Test
    public void testAdmin(){
        for (int i = 0; i < 100; i++){
            adminMapper.insert(new Admin(null, "loginAccount" + i, "userPswd" + i, "userName" + i, "email" + i, null));
        }
    }

    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void testInsertAdmin(){
        Admin admin = new Admin(null, "lyx", "123456", "leeyen", "2653@qq.com", null);
        int count = adminMapper.insert(admin);
    }

    @Test
    public void testLogger(){
        Logger logger = LoggerFactory.getLogger(CrowdTest.class);
        logger.debug("i am debug level");
        logger.info("infomation");
        logger.warn("warnning");
        logger.error("erroring");
    }

    @Test
    public void testTx(){
        Admin admin = new Admin(null, "leeyen", "123456", "wori", "2653@qq.com", null);
        adminService.saveAdmin(admin);
    }
}
