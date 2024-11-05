package com.lixd.wifiserver.util

import android.annotation.SuppressLint
import com.blankj.utilcode.util.FileUtils
import com.lixd.wifiserver.server.response.FileDescription
import java.io.File
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import kotlin.math.log10
import kotlin.math.pow

object FileUtil {
    @SuppressLint("SimpleDateFormat")
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")

    fun getChildFiles(dir: File): List<FileDescription> {
        val files = mutableListOf<FileDescription>()
        if (dir.exists() && dir.isDirectory) {
            dir.listFiles()?.forEach {
                val childCount = if (it.isDirectory) {
                    it.listFiles()?.size ?: 0
                } else {
                    0
                }
                val description = if (it.isDirectory) {
                    "${DateUtil.format(it.lastModified(), dateFormat)} - ${childCount}项"
                } else {
                    "${
                        DateUtil.format(
                            it.lastModified(),
                            dateFormat
                        )
                    } - ${formatFileSize(it.length())}"
                }
                files.add(
                    FileDescription(
                        it.name,
                        it.absolutePath,
                        it.lastModified(),
                        description = description,
                        isFile = it.isFile,
                        childCount = childCount,
                        parentName = it.parentFile?.name ?: "",
                        parentPath = it.parentFile?.absolutePath ?: ""
                    )
                )
            }
        }
        return files
    }

    /**
     * 格式化文件大小
     */
    fun formatFileSize(size: Long): String {
        if (size <= 0) return "0 B"
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (log10(size.toDouble()) / log10(1024.0)).toInt()
        return DecimalFormat("#,##0.##").format(
            size / 1024.0.pow(digitGroups.toDouble())
        ) + " " + units[digitGroups]
    }
}

fun File.md5(): String {
    return FileUtils.getFileMD5ToString(this)
}