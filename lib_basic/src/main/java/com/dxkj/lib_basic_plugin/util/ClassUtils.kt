package com.dxkj.lib_basic_plugin.util

import android.content.Context
import android.util.Log
import kotlin.collections.ArrayList


object ClassUtils {

    private val TAG = "ClassUtils"

    /**
     * 获取多路径下所有实现了接口的类对象
     *
     * @param context  U know
     * @param clazz    接口
     * @param pathList 包路径列表
     * @param <T>      U know
     * @return 对象列表
    </T> */
    fun <T> getObjectsWithInterface(
        context: Context,
        clazz: Class<T>,
        pathList: List<String>
    ): List<T> {
        val objectList: MutableList<T> = ArrayList()
        pathList.forEach { className ->
            try {
                val aClass = Class.forName(className)
                if (clazz.isAssignableFrom(aClass) && clazz != aClass && !aClass.isInterface) {
                    objectList.add(Class.forName(className).getConstructor().newInstance() as T)
                }
            } catch (e: Exception) {
                e.printStackTrace();
                Log.e(TAG, "getObjectsWithInterface error, " + e.message)
            }
        }
        return objectList
    }


}