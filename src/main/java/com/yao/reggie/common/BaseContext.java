package com.yao.reggie.common;

/**
 * @author yao
 * @create 2022-11-07 14:33
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    public static void set(Long id){
        threadLocal.set(id);
    }
    public static Long get(){
        return threadLocal.get();
    }
}
