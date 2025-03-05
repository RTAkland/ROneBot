/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/28
 */

@file:Suppress("unused")

package cn.rtast.rob.entity

import com.google.gson.annotations.SerializedName

/**
 * 群文件存储信息
 */
public data class GroupFileSystemInfo(
    val data: FileSystemInfo
) {
    public data class FileSystemInfo(
        /**
         * 文件数
         */
        @SerializedName("file_count")
        val fileCount: Int,
        /**
         * 最多允许文件的数量
         */
        @SerializedName("limit_count")
        val limitCount: Int,
        /**
         * 已使用的存储空间, 单位 bytes 字节
         */
        @SerializedName("used_space")
        val usedSpace: Long,
        /**
         * 总存储空间, 单位 bytes 字节
         */
        @SerializedName("total_space")
        val totalSpace: Long,
    ) {
        /**
         * 快速获取已使用的存储空间并将其转换成MB
         */
        val usedMB: Long get() = usedSpace / (1024 * 1024)

        /**
         * 快速获取已使用的存储空间并将其转换成GB
         */
        val usedGB: Long get() = usedSpace / (1024 * 1024 * 1024)

        /**
         * 快速获取总存储空间并将其转换成MB
         */
        val totalMB: Long get() = totalSpace / (1024 * 1024)

        /**
         * 快速获取总存储空间并将其转换成GB
         */
        val totalGB: Long get() = totalSpace / (1024 * 1024 * 1024)
    }
}