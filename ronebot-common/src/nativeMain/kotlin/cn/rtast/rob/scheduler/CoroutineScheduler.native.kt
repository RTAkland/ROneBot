/*
 * Copyright © 2025 RTAkland
 * Date: 11/19/25, 6:30 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.scheduler

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

internal actual val schedulerScope: CoroutineScope = CoroutineScope(Dispatchers.Main)