/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/11
 */

package cn.rtast.rob.starter.backend.util

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

public fun zipFolder(folder: File, zipFile: File) {
    FileOutputStream(zipFile).use { fos ->
        ZipOutputStream(fos).use { zos ->
            zipRecursively(folder, folder.name, zos)
        }
    }
}

public fun zipRecursively(file: File, parentDir: String, zos: ZipOutputStream) {
    if (file.isDirectory) {
        val dirEntries = file.listFiles() ?: return
        for (entry in dirEntries) {
            zipRecursively(entry, "$parentDir/${entry.name}", zos)
        }
    } else {
        val zipEntry = ZipEntry(parentDir)
        zos.putNextEntry(zipEntry)
        FileInputStream(file).use { fis ->
            fis.copyTo(zos)
        }
        zos.closeEntry()
    }
}