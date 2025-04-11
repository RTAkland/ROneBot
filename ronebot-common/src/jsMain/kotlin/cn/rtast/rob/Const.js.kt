/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/11 13:24
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob

import cn.rtast.rob.annotations.InternalROneBotApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@InternalROneBotApi
public actual val commonCoroutineScope: CoroutineScope
    get() = CoroutineScope(Dispatchers.Default)