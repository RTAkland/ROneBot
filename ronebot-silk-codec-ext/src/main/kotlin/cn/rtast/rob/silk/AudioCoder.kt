/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/18
 */

@file:Suppress("unused")
@file:JvmName("AudioCoder")

package cn.rtast.rob.silk

import javazoom.jl.decoder.Bitstream
import javazoom.jl.decoder.Decoder
import javazoom.jl.decoder.SampleBuffer
import org.jflac.FLACDecoder
import org.jflac.PCMProcessor
import org.jflac.metadata.StreamInfo
import org.jflac.util.ByteData
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

fun mp3ToPCM(file: File): ByteArray {
    return mp3ToPCM(file.inputStream())
}

fun mp3ToPCM(inputStream: InputStream): ByteArray {
    val bitStream = Bitstream(inputStream)
    val decoder = Decoder()
    val outputStream = ByteArrayOutputStream()
    var header = bitStream.readFrame()
    while (header != null) {
        val sampleBuffer = decoder.decodeFrame(header, bitStream) as SampleBuffer
        val pcm = sampleBuffer.buffer
        for (sample in pcm) {
            outputStream.write(sample.toInt() and 0xFF)
            outputStream.write((sample.toInt() shr 8) and 0xFF)
        }
        bitStream.closeFrame()
        header = bitStream.readFrame()
    }
    return outputStream.toByteArray()
}

fun flacToPCM(file: File): ByteArray {
    return flacToPCM(file.inputStream())
}

fun flacToPCM(inputStream: InputStream): ByteArray {
    val decoder = FLACDecoder(inputStream)
    val outputStream = ByteArrayOutputStream()
    decoder.addPCMProcessor(object : PCMProcessor {
        override fun processStreamInfo(streamInfo: StreamInfo) {
        }

        override fun processPCM(pcm: ByteData) {
            outputStream.write(pcm.data)
        }
    })
    decoder.decode()
    inputStream.close()
    return outputStream.toByteArray()
}