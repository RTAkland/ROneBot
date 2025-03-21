/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/22
 */

package cn.rtast.rob

import cn.rtast.rob.util.ExcludeStrategy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

public val gson: Gson = GsonBuilder()
    .disableHtmlEscaping()
    .setPrettyPrinting()
    .addSerializationExclusionStrategy(ExcludeStrategy())
    .addDeserializationExclusionStrategy(ExcludeStrategy())
    .create()

public actual val coroutineDispatcher: CoroutineDispatcher
    get() = Dispatchers.IO
