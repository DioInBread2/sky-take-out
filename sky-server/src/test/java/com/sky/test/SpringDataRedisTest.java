package com.sky.test;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

//@SpringBootTest
public class SpringDataRedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedisTemplate() {
        System.out.println(redisTemplate);
        ValueOperations valueOperations1 = redisTemplate.opsForValue();
        ListOperations listOperations = redisTemplate.opsForList();
        SetOperations setOperations = redisTemplate.opsForSet();
        HashOperations hashOperations = redisTemplate.opsForHash();
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
    }

    /**
     * 操作字符串
     */
    @Test
    public void testString() {
        // set get setex setnx
        redisTemplate.opsForValue().set("city", "北京");
        String city =(String)redisTemplate.opsForValue().get("city");
        System.out.println(city);
        redisTemplate.opsForValue().set("code", "123456", 3, TimeUnit.MINUTES);
        redisTemplate.opsForValue().setIfAbsent("lock", "lock");
        redisTemplate.opsForValue().setIfAbsent("lock", "lock3");
    }

    /**
     * 操作哈希类型
     */
    @Test
    public void testHash() {
        //hset hget hdel hkeys hvals
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put("user:1", "name", "zhangsan");
        hashOperations.put("user:1", "age", "20");
        String name = (String) hashOperations.get("user:1", "name");
        System.out.println(name);


        Set keys = hashOperations.keys("user:1");
        System.out.println(keys);
        List values = hashOperations.values("user:1");
        System.out.println(values);
        hashOperations.delete("user:1", "name");
        hashOperations.delete("user:1","age");
    }

    /**
     * 操作列表类型数据
     */
    @Test
    public  void testList() {
        //lpush lpop lrange rpop
        ListOperations listOperations = redisTemplate.opsForList();
        listOperations.leftPush("mylist", "aaa");
        listOperations.leftPush("mylist", "bbb");
        listOperations.leftPush("mylist", "ccc");
        List<String> mylist = listOperations.range("mylist", 0, -1);
        for (String s : mylist) {
            System.out.println(s);
        }
        listOperations.rightPop("mylist");
        List<String> mylist2 = listOperations.range("mylist", 0, -1);
        for (String s : mylist2) {
            System.out.println(s);
        }
    }

    /**
     * 操作集合类型
     */
    @Test
    public void testSet() {
        SetOperations setOperations = redisTemplate.opsForSet();
        setOperations.add("mySet", "aaa", "bbb", "ccc");
        setOperations.add("mySet2", "aaa", "bbe", "ccc");
        Set mySet = setOperations.members("mySet");
        for (Object o : mySet) {
            System.out.println(o);
        }
        setOperations.remove("mySet", "aaa");
        Set mySet2 = setOperations.members("mySet");
        for (Object o : mySet2) {
            System.out.println(o);
        }
        setOperations.pop("mySet");
        Set mySet3 = setOperations.members("mySet");
        for (Object o : mySet3) {
            System.out.println(o);
        }
        setOperations.remove("mySet", "bbb");
        Set union = setOperations.union("mySet", "mySet2");
        for (Object o : union) {
            System.out.println(o);
        }
        Set intersect = setOperations.intersect("mySet", "mySet2");
        for (Object o : intersect) {
            System.out.println(o);
        }
        Set difference = setOperations.difference("mySet", "mySet2");
        for (Object o : difference) {
            System.out.println(o);
        }
    }

    /**
     * 有序集合操作
     */
    @Test
    public void testZSet() {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add("myzset", "aaa", 1);
        zSetOperations.add("myzset", "bbb", 2);
        zSetOperations.add("myzset", "ccc", 3);

        Set myzset = zSetOperations.range("myzset", 0, -1);
        for (Object o : myzset) {
            System.out.println(o);
        }
        zSetOperations.incrementScore("myzset", "aaa", 10);
        zSetOperations.remove("myzset", "bbb","ccc");
    }
    /**
     * 通用操作
     */
    @Test
    public void testCommon() {
        Set keys = redisTemplate.keys("*");
        for (Object key : keys) {
            System.out.println(key);
        }
        Boolean city = redisTemplate.hasKey("city");

        for (Object key : keys){
            DataType type = redisTemplate.type(key);
            System.out.println(type.name());
        }

        redisTemplate.delete("myset");

    }
}
