/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/8
 */


package cn.rtast.rob.entity

import cn.rtast.rob.entity.internal.FileEventActionable
import com.google.gson.annotations.SerializedName
import java.io.FileOutputStream
import java.net.URI

data class FileEvent(
    @SerializedName("group_id")
    val groupId: Long?,
    @SerializedName("user_id")
    val userId: Long,
    val file: File,
) : FileEventActionable {
    data class File(
        val id: String,
        val name: String,
        val size: Int,
        val url: String
    )

    override suspend fun saveTo(path: String) {
        this.saveTo(java.io.File(path, file.name))
    }

    override suspend fun saveTo(file: java.io.File) {
        println("Saving ${this@FileEvent.file.name} to ${file.path}")
        val connection = URI(this@FileEvent.file.url).toURL().openConnection()
        connection.inputStream.use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                val buffer = ByteArray(4096)
                var bytesRead: Int
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }
            }
        }
    }
}