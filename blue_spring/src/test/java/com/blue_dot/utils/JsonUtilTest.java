package com.blue_dot.utils;

import cn.hutool.json.JSONUtil;
import com.blue_dot.test.TestEntity1;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.*;

/**
 * @Author Jason
 * @CreationDate 2023/02/10 - 14:41
 * @Description ：
 */
public class JsonUtilTest {
    @Test
    public void toJsonStrTest(){
        System.out.println(JSONUtil.toJsonStr(new TestEntity1("1223",2)));  // {"a":"1223","b":2}

        /*-------------------------------------------------------------------------------------------------*/

        Map<Integer, Integer> map = new HashMap<>();
        map.put(1,1);
        map.put(5,6);
        map.put(63,9);
        map.put(4,5);
        map.put(99,4);

        System.out.println(JSONUtil.toJsonStr(map));    // {"99":4,"1":1,"4":5,"5":6,"63":9}

        /*-------------------------------------------------------------------------------------------------*/

        ArrayList<Integer> list = Lists.newArrayList(15, 13, 444, 666);
        System.out.println(JSONUtil.toJsonStr(list));    // [15,13,444,666]

        /*-------------------------------------------------------------------------------------------------*/

        HashSet<Integer> set = new HashSet<>();
        set.add(55);
        set.add(44);
        set.add(1518);
        set.add(7);
        set.add(898);
        set.add(8);
        System.out.println(JSONUtil.toJsonStr(set));    // [898,55,7,8,44,1518]

    }
}
