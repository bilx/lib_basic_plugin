package com.dxkj.lib_basic_plugin.base

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import com.dxkj.lib_basic_plugin.util.ClassUtils

open class BaseApplication : Application() {

    companion object {
        lateinit var instance: BaseApplication
    }

    private var mAppDelegate: AppDelegate? = null


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        instance = this

        if (mAppDelegate == null) {
            mAppDelegate = AppDelegate(this)
        }

        mAppDelegate?.attachBaseContext(this)

    }

    override fun onCreate() {
        super.onCreate()
        mAppDelegate?.onCreate(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        mAppDelegate?.onTerminate()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mAppDelegate?.onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        mAppDelegate?.onTrimMemory(level)
    }



}