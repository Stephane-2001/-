package com.leeyen.crowd.mvc.controller;

import com.leeyen.crowd.entity.Menu;
import com.leeyen.crowd.service.api.MenuService;
import com.leeyen.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @ResponseBody
    @RequestMapping("/save.json")
    public ResultEntity<String> saveMenu(Menu menu) {
        menuService.saveMenu(menu);
        return ResultEntity.successWithoutData();
    }


    @RequestMapping("/get/whole/tree.json")
    @ResponseBody
    public ResultEntity<Menu> getWholeTreeNew(){

        // 1.查询全部的menu对象
        List<Menu> menuList = menuService.getAll();

        // 2.声明一个变量存储找到的根节点
        Menu root = null;

        // 3.创建map对象来存储id和menu对象的相应关系
        Map<Integer, Menu> menuMap = new HashMap<>();

        // 4.遍历list填充map对象
        for (Menu menu : menuList){
            Integer id = menu.getId();

            menuMap.put(id, menu);
        }

        // 5.遍历list查找根节点
        for (Menu menu : menuList){
            Integer pid = menu.getPid();

            if (pid == null){
                root = menu;
                continue;
            }
            Menu father = menuMap.get(pid);

            father.getChildren().add(menu);
        }

        return ResultEntity.successWithData(root);
    }
}
