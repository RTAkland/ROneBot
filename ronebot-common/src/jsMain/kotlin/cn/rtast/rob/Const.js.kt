/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2025/03/23
 */


package cn.rtast.rob

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

public actual val commonCoroutineScope: CoroutineScope
    get() = CoroutineScope(Dispatchers.Default)