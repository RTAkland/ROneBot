/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/16
 */

@file:Suppress("unused")

package cn.rtast.rob.silk

import io.github.kasukusakura.silkcodec.SilkCoder
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

/**
 * 从一个[InputStream]的PCM音频格式转换成Silk格式
 */
fun InputStream.encodeToSilk(sampleRate: Int): ByteArray {
    val outputStream = ByteArrayOutputStream()
    SilkCoder.encode(this, outputStream, sampleRate)
    return outputStream.toByteArray()
}

/**
 * 从一个[File]的PCM音频格式转换成Silk格式
 */
fun File.encodeToSilk(sampleRate: Int): ByteArray {
    val outputStream = ByteArrayOutputStream()
    SilkCoder.encode(this.inputStream(), outputStream, sampleRate)
    return outputStream.toByteArray()
}
