/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 9/16/25, 12:54 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.starter.frontend.components

import dev.fritz2.core.RenderContext
import dev.fritz2.core.Store

public fun RenderContext.showDialog(
    showDialog: Store<Boolean>,
    title: String,
    message: String? = null,
    content: RenderContext.() -> Unit,
    dialogAction: RenderContext.() -> Unit
) {
    showDialog.data.render { visible ->
        if (visible) {
            div("modal is-active") {
                inlineStyle("z-index: 9999;")
                div("modal-background") {
                    clicks handledBy { showDialog.update(false) }
                }
                div("modal-card") {
                    header("modal-card-head") {
                        p("modal-card-title") { +title }
                        button("delete") {
                            attr("aria-label", "close")
                            clicks handledBy { showDialog.update(false) }
                        }
                    }
                    section("modal-card-body") {
                        if (message != null) p { +message }
                        content()
                    }
                    footer("modal-card-foot") {
                        div("buttons is-right") {
                            button("button is-right") {
                                i("fa-solid fa-ban mr-2") {}
                                +"Cancel"
                                clicks handledBy { showDialog.update(false) }
                            }
                            button("button is-info is-right") {
                                i("fa-solid fa-check mr-2") {}
                                +"Confirm"
                                clicks handledBy {
                                    showDialog.update(false)
                                    dialogAction()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
