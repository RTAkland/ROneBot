/*
 * Copyright © 2025 RTAkland
 * Date: 10/5/25, 3:51 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


@file:OptIn(ExperimentalUnsignedTypes::class)

package cn.rtast.rob.onebot.stream.util


internal class Sha256Stream {
    private var h0 = 0x6a09e667u
    private var h1 = 0xbb67ae85u
    private var h2 = 0x3c6ef372u
    private var h3 = 0xa54ff53au
    private var h4 = 0x510e527fu
    private var h5 = 0x9b05688cu
    private var h6 = 0x1f83d9abu
    private var h7 = 0x5be0cd19u

    private val buffer = ByteArray(64)
    private var bufferSize = 0
    private var totalBytes: ULong = 0u

    private val k = uintArrayOf(
        0x428a2f98u, 0x71374491u, 0xb5c0fbcfu, 0xe9b5dba5u, 0x3956c25bu, 0x59f111f1u, 0x923f82a4u, 0xab1c5ed5u,
        0xd807aa98u, 0x12835b01u, 0x243185beu, 0x550c7dc3u, 0x72be5d74u, 0x80deb1feu, 0x9bdc06a7u, 0xc19bf174u,
        0xe49b69c1u, 0xefbe4786u, 0x0fc19dc6u, 0x240ca1ccu, 0x2de92c6fu, 0x4a7484aau, 0x5cb0a9dcu, 0x76f988dau,
        0x983e5152u, 0xa831c66du, 0xb00327c8u, 0xbf597fc7u, 0xc6e00bf3u, 0xd5a79147u, 0x06ca6351u, 0x14292967u,
        0x27b70a85u, 0x2e1b2138u, 0x4d2c6dfcu, 0x53380d13u, 0x650a7354u, 0x766a0abbu, 0x81c2c92eu, 0x92722c85u,
        0xa2bfe8a1u, 0xa81a664bu, 0xc24b8b70u, 0xc76c51a3u, 0xd192e819u, 0xd6990624u, 0xf40e3585u, 0x106aa070u,
        0x19a4c116u, 0x1e376c08u, 0x2748774cu, 0x34b0bcb5u, 0x391c0cb3u, 0x4ed8aa4au, 0x5b9cca4fu, 0x682e6ff3u,
        0x748f82eeu, 0x78a5636fu, 0x84c87814u, 0x8cc70208u, 0x90befffau, 0xa4506cebu, 0xbef9a3f7u, 0xc67178f2u
    )

    fun update(data: ByteArray) {
        var offset = 0
        totalBytes += data.size.toULong()
        while (offset < data.size) {
            val space = 64 - bufferSize
            val toCopy = minOf(space, data.size - offset)
            data.copyInto(buffer, bufferSize, offset, offset + toCopy)
            bufferSize += toCopy
            offset += toCopy
            if (bufferSize == 64) {
                compress(buffer)
                bufferSize = 0
            }
        }
    }

    fun finalize(): ByteArray {
        val padding = ByteArray(64)
        padding[0] = 0x80.toByte()
        val lengthBits = totalBytes * 8u
        val finalBlock = if (bufferSize < 56) {
            buffer.copyOf(bufferSize) + padding.copyOf(56 - bufferSize)
        } else {
            buffer.copyOf(bufferSize) + padding.copyOf(64 + 56 - bufferSize)
        }
        val lengthBytes = ByteArray(8)
        for (i in 0 until 8) {
            lengthBytes[7 - i] = ((lengthBits shr (i * 8)).toByte())
        }
        update(finalBlock)
        update(lengthBytes)

        val result = ByteArray(32)
        h0.toBytes(result, 0)
        h1.toBytes(result, 4)
        h2.toBytes(result, 8)
        h3.toBytes(result, 12)
        h4.toBytes(result, 16)
        h5.toBytes(result, 20)
        h6.toBytes(result, 24)
        h7.toBytes(result, 28)
        return result
    }

    private fun UInt.toBytes(array: ByteArray, offset: Int) {
        array[offset] = ((this shr 24) and 0xFFu).toByte()
        array[offset + 1] = ((this shr 16) and 0xFFu).toByte()
        array[offset + 2] = ((this shr 8) and 0xFFu).toByte()
        array[offset + 3] = (this and 0xFFu).toByte()
    }

    private fun compress(block: ByteArray) {
        val w = UIntArray(64)
        for (t in 0 until 16) {
            w[t] = ((block[t * 4].toUInt() and 0xFFu) shl 24) or
                    ((block[t * 4 + 1].toUInt() and 0xFFu) shl 16) or
                    ((block[t * 4 + 2].toUInt() and 0xFFu) shl 8) or
                    ((block[t * 4 + 3].toUInt() and 0xFFu))
        }
        for (t in 16 until 64) {
            val s0 = w[t - 15].rotateRight(7) xor w[t - 15].rotateRight(18) xor (w[t - 15] shr 3)
            val s1 = w[t - 2].rotateRight(17) xor w[t - 2].rotateRight(19) xor (w[t - 2] shr 10)
            w[t] = w[t - 16] + s0 + w[t - 7] + s1
        }

        var a = h0; var b = h1; var c = h2; var d = h3
        var e = h4; var f = h5; var g = h6; var h = h7

        for (t in 0 until 64) {
            val s1 = e.rotateRight(6) xor e.rotateRight(11) xor e.rotateRight(25)
            val ch = (e and f) xor (e.inv() and g)
            val temp1 = h + s1 + ch + k[t] + w[t]
            val s0 = a.rotateRight(2) xor a.rotateRight(13) xor a.rotateRight(22)
            val maj = (a and b) xor (a and c) xor (b and c)
            val temp2 = s0 + maj

            h = g; g = f; f = e; e = d + temp1
            d = c; c = b; b = a; a = temp1 + temp2
        }

        h0 += a; h1 += b; h2 += c; h3 += d
        h4 += e; h5 += f; h6 += g; h7 += h
    }

    private fun UInt.rotateRight(bits: Int): UInt = (this shr bits) or (this shl (32 - bits))
}