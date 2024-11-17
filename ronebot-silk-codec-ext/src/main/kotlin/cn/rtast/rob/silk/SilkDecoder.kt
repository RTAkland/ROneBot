/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/17
 */

@file:Suppress("unused")
@file:JvmName("SilkDecoder")

package cn.rtast.rob.silk

import io.github.kasukusakura.silkcodec.SilkCoder
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

/**
 * 从一个[InputStream]转换成PCM格式的音频, 但是需要参数传入
 */
fun InputStream.decodeToSilk(sampleRate: Int, strict: Boolean, loss: Int): ByteArray {
    val outputStream = ByteArrayOutputStream()
    SilkCoder.decode(this, outputStream, strict, sampleRate, loss)
    return outputStream.toByteArray()
}

/**
 * 从一个[File]转换成PCM格式的音频, 但是需要参数传入
 */
fun File.decodeToSilk(sampleRate: Int, strict: Boolean, loss: Int): ByteArray {
    val outputStream = ByteArrayOutputStream()
    SilkCoder.decode(this.inputStream(), outputStream, strict, sampleRate, loss)
    return outputStream.toByteArray()
}

/**
 * 从一个[InputStream]转换成PCM格式的音频
 */
fun InputStream.decodeToSilk(): ByteArray {
    val outputStream = ByteArrayOutputStream()
    SilkCoder.decode(this, outputStream)
    return outputStream.toByteArray()
}

/**
 * 从一个[File]对象转换成PCM格式的音频
 */
fun File.decodeToSilk(): ByteArray {
    val outputStream = ByteArrayOutputStream()
    SilkCoder.decode(this.inputStream(), outputStream)
    return outputStream.toByteArray()
}