package com.leeyen.crowd.service.api;

import com.leeyen.crowd.entity.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> getAll();

    void saveMenu(Menu menu);
}
