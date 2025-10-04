/*
 * Copyright © 2025 RTAkland
 * Date: 10/5/25, 5:06 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.stream

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.channels.Channel

internal sealed class PendingRequest {
    class Single(val deferred: CompletableDeferred<String>) : PendingRequest()
    class Stream(val channel: Channel<String>) : PendingRequest()
}