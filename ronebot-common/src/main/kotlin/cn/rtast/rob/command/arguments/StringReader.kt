/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/1/16
 */


package cn.rtast.rob.command.arguments

import com.mojang.brigadier.StringReader
import com.mojang.brigadier.exceptions.CommandSyntaxException

/**
 * 读取一个[Char]
 */
@Throws(CommandSyntaxException::class)
fun StringReader.readChar(): Char {
    if (!canRead()) {
        throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerExpectedBool().createWithContext(this)
    }
    return read()
}