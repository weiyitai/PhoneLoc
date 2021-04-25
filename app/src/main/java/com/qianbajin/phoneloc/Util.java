package com.qianbajin.phoneloc;

/**
 * ----------------------
 * 代码千万行
 * 注释第一行
 * 代码不注释
 * 改bug两行泪
 * -----------------------
 *
 * @author  weiyitai
 * @date 2020/1/3
 */
public class Util {

    private static Object sInterceptor;
    private static ClassLoader sClassLoader;

    public static ClassLoader getClassLoader() {
        return sClassLoader;
    }

    public static void setClassLoader(ClassLoader classLoader) {
        sClassLoader = classLoader;
    }

}
