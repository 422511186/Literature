package com.hzy.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @Auther: hzy
 * @Date: 2021/10/30 17:19
 * @Description:
 */


public class SerializeUtils {


    //序列化
    public static String serializeToString(Object obj) throws Exception {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
        objOut.writeObject(obj);
        return byteOut.toString("ISO-8859-1");

    }

    //反序列化
    public static Object deserializeToObject(String str) throws Exception {
        ByteArrayInputStream byteIn = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
        ObjectInputStream objIn = new ObjectInputStream(byteIn);
        return objIn.readObject();
    }
}
