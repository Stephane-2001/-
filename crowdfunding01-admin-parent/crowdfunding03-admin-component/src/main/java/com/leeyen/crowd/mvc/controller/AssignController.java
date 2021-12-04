package com.leeyen.crowd.mvc.controller;

import com.leeyen.crowd.entity.Auth;
import com.leeyen.crowd.entity.Role;
import com.leeyen.crowd.service.api.AdminService;
import com.leeyen.crowd.service.api.AuthService;
import com.leeyen.crowd.service.api.RoleService;
import com.leeyen.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/assign")
public class AssignController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @ResponseBody
    @RequestMapping("/do/role/assign/auth.json")
    public ResultEntity<String> saveRoleAuthRelathinship(
            @RequestBody Map<String, List<Integer>> map) {
        authService.saveRoleAuthRelathinship(map);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/get/assigned/auth/id/by/role/id.json")
    @ResponseBody
    public ResultEntity<List<Integer>> getAssignedAuthIdByRoleId(
            @RequestParam("roleId") Integer roleId
    ){
        List<Integer> authIdList = authService.getAssignedAuthIdByRoleId(roleId);
        return ResultEntity.successWithData(authIdList);
    }

    @RequestMapping("/get/all/auth.json")
    @ResponseBody
    public ResultEntity<List<Auth>> getAllAuth(){
        List<Auth> authList = authService.getAllAuth();
        return ResultEntity.successWithData(authList);
    }

    @RequestMapping("/do/role/assign.html")
    public String saveAdminRoleRelatonship(
            @RequestParam("adminId") Integer adminId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "roleIdList", required = false) List<Integer> roleIdList
    ){
        adminService.saveAdminRoleRelatonship(adminId, roleIdList);

        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }

    @RequestMapping("/to/assign/role/page.html")
    public String toAssignRolePage(
            @RequestParam("adminId") Integer adminId,
            ModelMap modelMap
    ){
        // 1.查询已经分配的角色
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);
        // 2.查询未被分配的角色
        List<Role> unAssignedRoleList = roleService.getUnAssignedRole(adminId);
        // 3.存入模型
        modelMap.addAttribute("assignedRoleList", assignedRoleList);
        modelMap.addAttribute("unAssignedRoleList", unAssignedRoleList);

        return "assign-role";
    }

}
