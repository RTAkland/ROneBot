/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/7
 */


package cn.rtast.rob.common

import cn.rtast.rob.common.util.ExcludeStrategy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

val gson: Gson = GsonBuilder()
    .disableHtmlEscaping()
    .addSerializationExclusionStrategy(ExcludeStrategy())
    .addDeserializationExclusionStrategy(ExcludeStrategy())
    .create()