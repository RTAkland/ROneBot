/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/22
 */

package cn.rtast.rob

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

public actual val coroutineDispatcher: CoroutineDispatcher
    get() = Dispatchers.Default