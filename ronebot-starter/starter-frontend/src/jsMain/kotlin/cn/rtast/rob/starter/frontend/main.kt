/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 9/16/25, 12:34 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.starter.frontend

import cn.rtast.rob.starter.frontend.pages.homePage
import dev.fritz2.core.render
import dev.fritz2.headless.components.toastContainer
import dev.fritz2.routing.Router
import dev.fritz2.routing.routerOf
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import kotlinx.browser.document
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


public val coroutineScope: CoroutineScope = MainScope()
public var backend: String = "http://127.0.0.1:9099"
public val router: Router<String> = routerOf("contents")
public var currentPath: String = ""
public val client: HttpClient = HttpClient(CIO)

public fun main() {
    toastContainer("default", "toast-container")
    coroutineScope.launch {
//        backend = http("/config.json").get().body().fromJson<FrontendConfig>().backend
        document.title = "ROB模板项目生成器"
        render("#target") {
            main {
                inlineStyle("flex-grow: 1;")
                router.data.render { site ->
                    currentPath = site
                    homePage()
                }
            }
        }
    }
}