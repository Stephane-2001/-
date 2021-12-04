package com.leeyen.springboot.test;

import com.leeyen.springboot.entity.Emp;
import com.leeyen.springboot.mapper.EmpMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@MapperScan("com.leeyen.springboot.mapper")
@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisTest {
    @Autowired
    private EmpMapper empMapper;

    private Logger logger = LoggerFactory.getLogger(MybatisTest.class);

    @Test
    public void testMapper() {
        List<Emp> list = empMapper.selectAll();
        for (Emp emp : list) {
            logger.debug(emp.toString());
        }
    }
}
