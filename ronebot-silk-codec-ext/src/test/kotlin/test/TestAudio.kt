/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/18
 */


package test

import cn.rtast.rob.silk.decodeToPCM
import java.io.File

fun main() {
//    val audioPath = "C:\\Users\\RTAkl\\Documents\\录音\\录音 (2).m4a"
//    val bytes = File(audioPath).encodeToSilk(8000)
    val bytes = File("./a.silk").decodeToPCM()
    File("./a.wav").writeBytes(bytes)
}