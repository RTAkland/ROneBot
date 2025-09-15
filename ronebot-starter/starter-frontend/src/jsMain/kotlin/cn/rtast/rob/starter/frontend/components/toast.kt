/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 9/16/25, 12:53 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.starter.frontend.components

import dev.fritz2.headless.components.toast
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds


public fun warningToast(message: String, duration: Duration = 2.5.seconds): Unit =
    toast("default", duration.inWholeMilliseconds) {
        div("notification is-warning is-light p-4 toast-message mb-2") {
            i("fas fa-exclamation-triangle mr-2") {}
            +message
        }
    }

public fun errorToast(message: String, duration: Duration = 2.5.seconds): Unit =
    toast("default", duration.inWholeMilliseconds) {
        div("notification is-danger is-light p-4 toast-message mb-2") {
            i("fas fa-ban mr-2") {}
            +message
        }
    }

public fun infoToast(message: String, duration: Duration = 2.5.seconds): Unit =
    toast("default", duration.inWholeMilliseconds) {
        div("notification is-info is-light p-4 toast-message mb-2") {
            i("fas fa-info-circle mr-2") {}
            +message
        }
    }