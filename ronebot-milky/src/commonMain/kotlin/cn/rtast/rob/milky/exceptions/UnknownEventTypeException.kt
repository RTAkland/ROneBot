/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 9/11/25, 2:33 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.exceptions

public class UnknownEventTypeException(name: String) : Exception("未知事件类型: $name")