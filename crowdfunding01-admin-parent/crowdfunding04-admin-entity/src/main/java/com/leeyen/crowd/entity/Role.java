package com.leeyen.crowd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Role {
    private Integer id;

    private String name;

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role() {
    }
}
