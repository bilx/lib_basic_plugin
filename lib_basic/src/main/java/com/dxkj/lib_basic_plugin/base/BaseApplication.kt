package com.dxkj.lib_basic_plugin.base

import android.app.Application
import android.content.pm.PackageManager
import android.util.Log

open class BaseApplication : Application() {


    companion object {
        lateinit var instance: BaseApplication
    }

    private var mAppDelegateList: List<IApplicationDelegate>? = null

    //包名
    private var mDelegateNameList: MutableList<String> = mutableListOf()

    override fun onCreate() {
        super.onCreate()
        instance = this
        init()
    }


    private fun init() {
        //读取 meta-data中的包名
        val applicationInfo = this.packageManager.getApplicationInfo(
            this.packageName,
            PackageManager.GET_META_DATA
        )

        applicationInfo.metaData.keySet().forEach { key ->
            val value = applicationInfo.metaData[key]?.toString()
            if (key.startsWith("delegate_name") && !value.isNullOrEmpty()) {
                mDelegateNameList.add(value)
            }
        }

        //初始化appDelegate 列表
        mAppDelegateList = ClassUtils.getObjectsWithInterface(
            this,
            IApplicationDelegate::class.java, mDelegateNameList
        )

        ///执行 appDelegate的回调方法
        mAppDelegateList?.forEach { delegate ->
            delegate.onCreate()
        }
    }


    override fun onTerminate() {
        super.onTerminate()
        mAppDelegateList?.forEach { delegate ->
            delegate.onTerminate()
        }
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        mAppDelegateList?.forEach { delegate ->
            delegate.onTrimMemory(level)
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mAppDelegateList?.forEach { delegate ->
            delegate.onLowMemory()
        }
    }

}