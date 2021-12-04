package com.leeyen.springboot.test;

import com.leeyen.springboot.entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MySpringBootTest<StringRedisTemplate> {

    @Autowired
    private Student student;

    Logger logger = LoggerFactory.getLogger(MySpringBootTest.class);

    @Test
    public void testReadYaml(){

        logger.info(student.toString());
    }


}
