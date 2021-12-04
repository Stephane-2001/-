package com.leeyen.springboot.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private Logger logger = LoggerFactory.getLogger(RedisTest.class);

    @Test
    public void testStringRedisTemplate() {

        // 获取ValueOperations对象
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();

        // 准备数据
        String key = "Hello";
        String value = "Hi";

        // 存入数据
        operations.set(key, value);

        // 读取数据
        String readValue = operations.get(key);

        // 输出
        logger.debug(readValue);
    }

    @Test
    public void testStringRedisTemplateForList() {

        // 获取ValueOperations对象
        ListOperations<String, String> operations = stringRedisTemplate.opsForList();

        // 准备数据
        String key = "fruit";
        List<String> values = new ArrayList<String>();
        values.add("apple");
        values.add("orange");
        values.add("banana");

        // 存入数据
        operations.leftPushAll(key, values);

        // 读取数据
        List<String> readValues = operations.range(key, 0, -1);

        // 输出
        logger.debug(readValues.toString());
    }

}
