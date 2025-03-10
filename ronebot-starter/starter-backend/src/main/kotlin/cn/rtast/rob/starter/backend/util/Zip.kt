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

public fun zipDirectory(inputDir: File, outputZip: File) {
    ZipOutputStream(FileOutputStream(outputZip)).use { zipOut ->
        inputDir.walkTopDown().forEach { file ->
            if (file.isFile && file.extension != "zip") {
                val zipEntry = ZipEntry(file.relativeTo(inputDir).path)
                zipOut.putNextEntry(zipEntry)
                FileInputStream(file).use { fis ->
                    fis.copyTo(zipOut)
                }
                zipOut.closeEntry()
            }
        }
    }
}