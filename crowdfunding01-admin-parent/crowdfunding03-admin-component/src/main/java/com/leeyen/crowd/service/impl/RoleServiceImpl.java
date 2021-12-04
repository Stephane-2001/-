package com.leeyen.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leeyen.crowd.entity.Role;
import com.leeyen.crowd.entity.RoleExample;
import com.leeyen.crowd.mapper.RoleMapper;
import com.leeyen.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;



    @Override
    public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {
        // 1.开启分页查询功能
        PageHelper.startPage(pageNum, pageSize);

        // 2.调用mapper的方法
        List<Role> list = roleMapper.selectRoleByKeyWord(keyword);

        // 3.封装为pageInfo对象
        return new PageInfo<>(list);
    }

    @Override
    public void saveRole(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKeySelective(role);

    }

    @Override
    public void removeList(List<Integer> roleList) {
        RoleExample roleExample = new RoleExample();

        RoleExample.Criteria criteria = roleExample.createCriteria();

        criteria.andIdIn(roleList);

        roleMapper.deleteByExample(roleExample);
    }

    @Override
    public List<Role> getAssignedRole(Integer adminId) {
        return roleMapper.selectAssignedRole(adminId);
    }

    @Override
    public List<Role> getUnAssignedRole(Integer adminId) {
        return roleMapper.selectUnAssignedRole(adminId);
    }

}
