package com.leeyen.springboot.mapper;

import com.leeyen.springboot.entity.Emp;

import java.util.List;

public interface EmpMapper {
    List<Emp> selectAll();
}
