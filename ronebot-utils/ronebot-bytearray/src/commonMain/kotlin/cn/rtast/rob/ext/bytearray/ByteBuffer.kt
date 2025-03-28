/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/28
 */

package cn.rtast.rob.ext.bytearray

public class ByteBuffer(private var buffer: ByteArray) {
    private var position = 0

    public constructor(capacity: Int) : this(ByteArray(capacity))

    public fun ensureCapacity(minCapacity: Int) {
        if (minCapacity > buffer.size) {
            buffer = buffer.copyOf(buffer.size * 2)
        }
    }

    public fun putByte(value: Byte) {
        ensureCapacity(position + 1)
        buffer[position] = value
        position++
    }

    public fun putInt(value: Int) {
        ensureCapacity(position + 4)
        for (i in 0..3) {
            buffer[position + i] = (value shr (24 - i * 8)).toByte()
        }
        position += 4
    }

    public fun putLong(value: Long) {
        ensureCapacity(position + 8)
        for (i in 0..7) {
            buffer[position + i] = (value shr (56 - i * 8)).toByte()
        }
        position += 8
    }

    public fun put(value: ByteArray) {
        ensureCapacity(position + value.size)
        value.copyInto(buffer, position)
        position += value.size
    }

    public fun getByte(index: Int = position): Byte {
        val value = buffer[index]
        position = index + 1
        return value
    }

    public fun getInt(index: Int = position): Int {
        val value = (buffer[index].toInt() and 0xFF shl 24) or
                (buffer[index + 1].toInt() and 0xFF shl 16) or
                (buffer[index + 2].toInt() and 0xFF shl 8) or
                (buffer[index + 3].toInt() and 0xFF)
        position = index + 4
        return value
    }

    public fun getLong(index: Int = position): Long {
        val value = (buffer[index].toLong() and 0xFF shl 56) or
                (buffer[index + 1].toLong() and 0xFF shl 48) or
                (buffer[index + 2].toLong() and 0xFF shl 40) or
                (buffer[index + 3].toLong() and 0xFF shl 32) or
                (buffer[index + 4].toLong() and 0xFF shl 24) or
                (buffer[index + 5].toLong() and 0xFF shl 16) or
                (buffer[index + 6].toLong() and 0xFF shl 8) or
                (buffer[index + 7].toLong() and 0xFF)
        position = index + 8
        return value
    }

    public fun toByteArray(): ByteArray = buffer.copyOf(position)
}