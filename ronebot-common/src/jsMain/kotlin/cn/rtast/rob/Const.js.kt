/*
 * Copyright © 2025 RTAkland
 * Date: 2025/12/16 17:04
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob

import cn.rtast.rob.annotations.InternalROneBotApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@InternalROneBotApi
public actual val commonCoroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
