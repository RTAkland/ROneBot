/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/20
 */


package cn.rtast.rob.installer

suspend fun main() {
    println("输入AccessToken")
    val accessToken = readLine()
    LagrangeInstaller(accessToken!!).extract()
}