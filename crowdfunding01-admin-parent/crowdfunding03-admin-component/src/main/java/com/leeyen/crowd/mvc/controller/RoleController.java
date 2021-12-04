package com.leeyen.crowd.mvc.controller;

import com.github.pagehelper.PageInfo;
import com.leeyen.crowd.entity.Role;
import com.leeyen.crowd.service.api.RoleService;
import com.leeyen.crowd.util.ResultEntity;
import org.apache.taglibs.standard.lang.jstl.test.beans.PublicBean1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/delete/by/role/id/array.json")
    @ResponseBody
    public ResultEntity<String> removeByRoleIdArray(
            @RequestBody List<Integer> roleList
    ){
        roleService.removeList(roleList);
        return ResultEntity.successWithoutData()
                ;
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public ResultEntity<String> updateRole(Role role){
        roleService.updateRole(role);
        return ResultEntity.successWithoutData();
    }

    @PreAuthorize("hasAuthority('user:save')")
    @RequestMapping("/save.json")
    @ResponseBody
    public ResultEntity<String> saveRole(Role role){
        roleService.saveRole(role);

        return ResultEntity.successWithoutData();
    }

    @PreAuthorize("hasRole('部长')")  //表示该处理必须拥有部长的权限才可以使用
    @RequestMapping("/get/page/info.json")
    @ResponseBody
    public ResultEntity<PageInfo<Role>> getPageInfo(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("keyword") String keyword
    ){
        PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);
        return ResultEntity.successWithData(pageInfo);
    }
}
