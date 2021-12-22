package com.dxkj.lib_basic_plugin.base

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import com.dxkj.lib_basic_plugin.util.ClassUtils

class AppDelegate constructor(context: Context) : AppLifecycle {


    private var appLifecycles: MutableList<AppLifecycle> = mutableListOf()
    private var appModules: MutableList<ConfigModule> = mutableListOf()


    init {
        //初始化modules配置
        initModules(context)

        //添加组件中的声明周期监听实现类
        appModules.forEach { configModule ->

            //app声明周期回调
            configModule.createAppLifecycle(context)?.let {
                appLifecycles.add(it)
            }
        }
    }


    /**
     * 初始化获取各个组件中的configModule
     */
    private fun initModules(context: Context) {

        val delegateNames = scanMetadataConfig(context)
        if (delegateNames.isEmpty()) {
            return
        }


        //初始化appDelegate 列表
        val configModules = ClassUtils.getObjectsWithInterface(
            context,
            ConfigModule::class.java, delegateNames
        )

        appModules.addAll(configModules)

    }

    /**
     * 扫描metadata 读取组件中 configModule 的实现类包名
     */
    private fun scanMetadataConfig(context: Context): List<String> {

        //读取 meta-data中的包名
        val applicationInfo = context.packageManager.getApplicationInfo(
            context.packageName,
            PackageManager.GET_META_DATA
        )

        val delegateNames = mutableListOf<String>()

        applicationInfo.metaData.keySet()?.forEach { key ->
            val value = applicationInfo.metaData[key]?.toString()
            if (key.startsWith("delegate_name") && !value.isNullOrEmpty()) {
                delegateNames.add(value)
            }
        }


        return delegateNames
    }


    override fun attachBaseContext(context: Context) {
        appLifecycles.forEach {
            it.attachBaseContext(context)
        }
    }


    override fun onCreate(context: Context) {
        appLifecycles.forEach {
            it.onCreate(context)
        }
    }

    override fun onTerminate() {
        appLifecycles.forEach {
            it.onTerminate()
        }
    }

    override fun onLowMemory() {
        appLifecycles.forEach {
            it.onLowMemory()
        }
    }

    override fun onTrimMemory(level: Int) {
        appLifecycles.forEach {
            it.onTrimMemory(level)
        }
    }


}