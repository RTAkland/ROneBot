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