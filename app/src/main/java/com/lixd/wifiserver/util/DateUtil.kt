package com.lixd.wifiserver.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

/**
 * 创建日期：2024/10/31 17:50
 * @author lixiaodong
 * @version 1.0
 * 类说明：
 */
object DateUtil {
    @SuppressLint("SimpleDateFormat")
    private val defaultDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    fun format(date: Long, dateFormat: SimpleDateFormat = defaultDateFormat): String {
        return dateFormat.format(Date(date))
    }
}