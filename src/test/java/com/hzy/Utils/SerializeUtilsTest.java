package com.hzy.Utils;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Auther: hzy
 * @Date: 2021/10/30 17:21
 * @Description:
 */
@Data
@ToString
@Accessors(chain = true)
class A implements Serializable {
    private int x;
    private String y;

}

class SerializeUtilsTest {
    public static void main(String[] args) throws Exception {
        HashMap<String, Object> map = new HashMap<>();

        map.put("key1", 1);
        map.put("key2", 2);
        map.put("key3", 3);
        map.put("key4", 4);
        map.put("key5", 5);
        System.out.println("map = " + map);
        Collection<Object> values = map.values();
        values.stream().forEach(System.out::println);
        System.out.println("map.entrySet() = " + map.entrySet());
    }

}