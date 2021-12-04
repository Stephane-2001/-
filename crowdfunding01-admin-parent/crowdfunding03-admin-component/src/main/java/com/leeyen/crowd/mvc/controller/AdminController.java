package com.leeyen.crowd.mvc.controller;

import com.github.pagehelper.PageInfo;
import com.leeyen.crowd.constant.CrowdConstant;
import com.leeyen.crowd.entity.Admin;
import com.leeyen.crowd.service.api.AdminService;
import com.leeyen.crowd.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/admin/update.html")
    public String update(
            Admin admin,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("keyword") String keyword
    ){
        adminService.update(admin);
        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }

    @RequestMapping("/admin/to/edit/page.html")
    public String toEditPage(
            @RequestParam("adminId") Integer adminId,
            ModelMap modelMap
    ){
        Admin admin = adminService.getAdminById(adminId);

        modelMap.addAttribute("admin", admin);

        return "admin-edit";
    }

    @RequestMapping("/admin/save.html")
    public String save(Admin admin){
        adminService.saveAdmin(admin);
        return "redirect:/admin/get/page.html?pageNum="+Integer.MAX_VALUE;
    }

    @RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
    public String removeAdmin(@PathVariable("adminId") Integer adminId,
                              @PathVariable("pageNum") Integer pageNum,
                              @PathVariable("keyword") String keyword
    ){
        adminService.remove(adminId);
        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }

    @RequestMapping("/admin/get/page.html")
    public String getPageInfo(
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            ModelMap modelMap
    ){
        // 1.调用service方法
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);

        // 2.存入模型
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);

        return "admin-page";
    }

    @RequestMapping("/admin/do/logout.html")
    public String doLogout(HttpSession session){
        // 强制session失效
        session.invalidate();

        return "redirect:/admin/to/login/page.html";
    }

    @RequestMapping("/admin/do/login.html")
    public String doLogin(
            @RequestParam(value = "loginAcct") String loginAcct,
            @RequestParam(value = "userPswd") String userPswd,
            HttpSession session
    ){
        Admin admin = adminService.getAdminByLoginAcct(loginAcct,userPswd);
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN,admin);
        return "redirect:/admin/to/main/page.html";
    }
}
