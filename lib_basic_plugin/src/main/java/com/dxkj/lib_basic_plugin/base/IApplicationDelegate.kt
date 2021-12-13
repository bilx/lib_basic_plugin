package com.dxkj.lib_basic_plugin.base

import android.content.Context


interface IApplicationDelegate {


    fun attachBaseContext(base: Context?)

    fun onCreate()

    fun onTerminate()

    fun onLowMemory()

    fun onTrimMemory(level: Int)

}