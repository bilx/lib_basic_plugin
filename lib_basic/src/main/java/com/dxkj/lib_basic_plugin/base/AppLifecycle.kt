package com.dxkj.lib_basic_plugin.base

import android.app.Application
import android.content.Context


/**
 * 跟踪 Application 生命周期的回调
 * 可以在自己的组件中声明实现类，在对应的回调方法中执行自己的操作

 */
interface AppLifecycle {

    fun attachBaseContext(context: Context)

    fun onCreate(context: Context)

    fun onTerminate()

    fun onLowMemory()

    fun onTrimMemory(level: Int)

}


/**
 * @keep 组件中的实现类不可以混淆
 */
interface ConfigModule {

    /**
     *  返回组件中需要跟踪app生命周期回调的接口实现类.
     *  BaseApp 会在对应Application的生命周期中调用实现类的方法
     */
    fun createAppLifecycle(context: Context): AppLifecycle? = null

}