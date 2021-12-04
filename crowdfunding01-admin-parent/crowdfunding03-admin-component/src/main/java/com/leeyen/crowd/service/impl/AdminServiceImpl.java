package com.leeyen.crowd.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leeyen.crowd.constant.CrowdConstant;
import com.leeyen.crowd.entity.Admin;
import com.leeyen.crowd.entity.AdminExample;
import com.leeyen.crowd.exception.LoginAcctAlreadyInUseException;
import com.leeyen.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.leeyen.crowd.exception.LoginFailedException;
import com.leeyen.crowd.mapper.AdminMapper;
import com.leeyen.crowd.service.api.AdminService;
import com.leeyen.crowd.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /***
     * 用与在页面上删除某个管理员
     * @param adminId
     */
    @Override
    public void remove(Integer adminId) {
         adminMapper.deleteByPrimaryKey(adminId);
    }

    @Override
    public Admin getAdminById(Integer adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }

    @Override
    public void update(Admin admin) {
        try {
            adminMapper.updateByPrimaryKeySelective(admin);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof DuplicateKeyException) {
                throw new LoginAcctAlreadyInUseForUpdateException(CrowdConstant.MESSAGE_LOGIN_ACCOUNT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public void saveAdminRoleRelatonship(Integer adminId, List<Integer> roleIdList) {
        // 1.删除旧的关系数据
        adminMapper.deleteOldRelationship(adminId);
        // 2.保存新的关系数据
        if (roleIdList != null && roleIdList.size() > 0){
            adminMapper.insertNewRelationShip(adminId, roleIdList);
        }


    }


    /***
     * pageHelper的翻页系统
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        // 1.调用pagehelper静态方法开启分页功能
        PageHelper.startPage(pageNum,pageSize);

        // 2.执行查询
        List<Admin> list = adminMapper.selectAdminByKeyWord(keyword);

        // 3.封装到pageInfo对象中
        return new PageInfo<>(list);
    }


    @Override
    public void saveAdmin(Admin admin) {
        // 1.密码加密
        String userPswd = admin.getUserPswd();
    /* 之前的加密方式
		userPswd = CrowdUtil.md5(userPswd);
		*/
        userPswd = passwordEncoder.encode(userPswd);
        admin.setUserPswd(userPswd);

        // 2.生成创建时间
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = format.format(date);
        admin.setCreateTime(createTime);

        // 3.执行保存
        try {
            adminMapper.insert(admin);
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof DuplicateKeyException) {
                throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCOUNT_ALREADY_IN_USE);
            }
        }
    }

    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    /***
     * 登录界面判断输入密码和数据库密码是否一致
     * @param loginAcct
     * @param userPswd
     * @return
     */
    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
        // 1.根据账号查询admin对象
            //创建adminExample对象
        AdminExample adminExample = new AdminExample();
            //创建Criteria对象
        AdminExample.Criteria criteria = adminExample.createCriteria();
            //在criteria中封装查询条件
        criteria.andLoginAcctEqualTo(loginAcct);
            //调用adminmapper执行查询
        List<Admin> list = adminMapper.selectByExample(adminExample);
        // 2.判断admin对象是否为null
        if (list == null || list.size() == 0){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        if (list.size() > 1){
            throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }
        // 3.如果为null则抛出异常
        Admin admin = list.get(0);
        if (admin == null){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        // 4.将数据库密码从admin中取出
        String userPswdDB = admin.getUserPswd();
        // 5.将表单提交的密码明文转换为密文
        String userPswdForm = CrowdUtil.md5(userPswd);
        // 6.和数据库密文进行比较
        if (!Objects.equals(userPswdDB,userPswdForm)){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        return admin;
    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct){
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(loginAcct);

        List<Admin> list = adminMapper.selectByExample(adminExample);

        Admin admin = list.get(0);

        return admin;
    }


}
