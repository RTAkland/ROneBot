/*
 * Copyright © 2026 RTAkland
 * Date: 2026/1/4 01:03
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob

import cn.rtast.rob.annotations.InternalROneBotApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

@InternalROneBotApi
public actual val commonCoroutineScope: CoroutineScope = MainScope()
