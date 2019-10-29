// IMyAidlInterface.aidl
package com.yuy.servicedemo;

// Declare any non-default types here with import statements

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL. 系统生成的，完全可以删掉。 这样build 后的java 文件就不会出现与它相关的
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);


    //此文件下定义自己的方法， 使得外部进程或者应用可以通过此方法获取 此进程某服务的进度
    //注意生成的aidl 文件仅仅是接口， 这里的方法按接口中的方法定义
    void showProgress();


}
