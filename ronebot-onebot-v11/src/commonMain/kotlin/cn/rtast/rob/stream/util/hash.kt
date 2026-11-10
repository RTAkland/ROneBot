/*
 * Copyright © 2025 RTAkland
 * Date: 10/5/25, 3:51 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.stream.util

private val K = intArrayOf(
    1116352408, 1899447441, -1245643825, -373957723,
    961987163, 1508970993, -1841331548, -1424204075,
    -670586216, 310598401, 607225278, 1426881987,
    1925078388, -2132889090, -1680079193, -1046744716,
    -459576895, -272742522, 264347078, 604807628,
    770255983, 1249150122, 1555081692, 1996064986,
    -1740746414, -1473132947, -1341970488, -1084653625,
    -958395405, -710438585, 113926993, 338241895,
    666307205, 773529912, 1294757372, 1396182291,
    1695183700, 1986661051, -2117940946, -1838011259,
    -1564481375, -1474664885, -1035236496, -949202525,
    -778901479, -694614492, -200395387, 275423344,
    430227734, 506948616, 659060556, 883997877,
    958139571, 1322822218, 1537002063, 1747873779,
    1955562222, 2024104815, -2067236844, -1933114872,
    -1866530822, -1538233109, -1090935817, -965641998
)

private fun step1(e: Int, f: Int, g: Int): Int =
    (e.rotateRight(6) xor e.rotateRight(11) xor e.rotateRight(25)) + ((e and f) xor (e.inv() and g))

private fun step2(a: Int, b: Int, c: Int): Int =
    (a.rotateRight(2) xor a.rotateRight(13) xor a.rotateRight(22)) + ((a and b) xor (a and c) xor (b and c))

public class SHA256Context internal constructor() {
    public val state: IntArray = IntArray(8)
    public val buffer: ByteArray = ByteArray(64)
    public var nBits: Long = 0L
    public var bufferCounter: Int = 0
    public fun init() {
        state[0] = 1779033703
        state[1] = -1150833019
        state[2] = 1013904242
        state[3] = -1521486534
        state[4] = 1359893119
        state[5] = -1694144372
        state[6] = 528734635
        state[7] = 1541459225
        nBits = 0
        bufferCounter = 0
    }

    public fun append(src: ByteArray) {
        for (i in src.indices) append(src[i])
    }

    public fun finalizeBytes(): ByteArray {
        finalize()
        val out = ByteArray(32)
        var idx = 0
        for (i in 0 until 8) for (j in 3 downTo 0) out[idx++] = ((state[i] ushr (j * 8)) and 0xff).toByte()
        return out
    }

    @Suppress("removal")
    public fun finalize() {
        val nBits = nBits
        append(0x80.toByte())
        while (bufferCounter != 56) append(0)
        for (i in 7 downTo 0) append(((nBits ushr (8 * i)) and 0xff).toByte())
    }

    public fun append(byte: Byte) {
        buffer[bufferCounter++] = byte
        nBits += 8
        if (bufferCounter == 64) {
            bufferCounter = 0
            process()
        }
    }

    private fun process() {
        val state = state
        var a = state[0]
        var b = state[1]
        var c = state[2]
        var d = state[3]
        var e = state[4]
        var f = state[5]
        var g = state[6]
        var h = state[7]

        val w = IntArray(16)

        for (i in 0 until 64 step 16) {
            updateW(w, i, buffer)
            for (j in 0 until 16 step 4) {
                var temp = h + step1(e, f, g) + K[i + j] + w[j]
                h = temp + d
                d = temp + step2(a, b, c)
                temp = g + step1(h, e, f) + K[i + j + 1] + w[j + 1]
                g = temp + c
                c = temp + step2(d, a, b)
                temp = f + step1(g, h, e) + K[i + j + 2] + w[j + 2]
                f = temp + b
                b = temp + step2(c, d, a)
                temp = e + step1(f, g, h) + K[i + j + 3] + w[j + 3]
                e = temp + a
                a = temp + step2(b, c, d)
            }
        }
        state[0] += a
        state[1] += b
        state[2] += c
        state[3] += d
        state[4] += e
        state[5] += f
        state[6] += g
        state[7] += h
    }

    private fun updateW(w: IntArray, i: Int, buffer: ByteArray) {
        if (i < 16) {
            for (j in 0 until 16) {
                w[j] = ((buffer[j * 4].toInt() and 0xff) shl 24) or
                        ((buffer[j * 4 + 1].toInt() and 0xff) shl 16) or
                        ((buffer[j * 4 + 2].toInt() and 0xff) shl 8) or
                        ((buffer[j * 4 + 3].toInt() and 0xff))
            }
        } else {
            for (j in 0 until 16) {
                val a = w[(j + 1) and 15]
                val b = w[(j + 14) and 15]
                val s0 = (a.rotateRight(7) xor a.rotateRight(18) xor (a ushr 3))
                val s1 = (b.rotateRight(17) xor b.rotateRight(19) xor (b ushr 10))
                w[j] += w[(j + 9) and 15] + s0 + s1
            }
        }
    }

    public companion object {
        public fun create(): SHA256Context = SHA256Context()
    }
}

public fun sha256Digest(src: ByteArray): ByteArray =
    SHA256Context().apply { init(); append(src) }.finalizeBytes()
