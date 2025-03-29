/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/27
 */

package cn.rtast.rob.exceptions

/**
 * [cn.rtast.rob.entity.Resource]转换成基本类型但是不是Base64资源时则会抛出异常
 * @see cn.rtast.rob.entity.toByteArray
 */
public class NonBase64ResourceException(message: String) : Exception(message)