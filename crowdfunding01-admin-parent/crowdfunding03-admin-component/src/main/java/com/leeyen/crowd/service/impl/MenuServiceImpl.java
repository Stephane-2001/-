package com.leeyen.crowd.service.impl;

import com.leeyen.crowd.entity.Menu;
import com.leeyen.crowd.entity.MenuExample;
import com.leeyen.crowd.mapper.MenuMapper;
import com.leeyen.crowd.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getAll() {
        return menuMapper.selectByExample(new MenuExample());
    }

    @Override
    public void saveMenu(Menu menu) {
        menuMapper.insert(menu);
    }
}
