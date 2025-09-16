/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 9/16/25, 12:51 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.starter.frontend.pages

import cn.rtast.rob.starter.common.GeneratorProperty
import cn.rtast.rob.starter.common.GradlePlugins
import cn.rtast.rob.starter.common.Language
import cn.rtast.rob.starter.common.ROneBotPlatform
import cn.rtast.rob.starter.frontend.backend
import cn.rtast.rob.starter.frontend.coroutineScope
import cn.rtast.rob.starter.frontend.util.getGradleVersion
import cn.rtast.rob.starter.frontend.util.getKotlinVersion
import cn.rtast.rob.starter.frontend.util.getROneBotVersion
import cn.rtast.rob.starter.frontend.util.toJson
import dev.fritz2.core.*
import kotlinx.browser.window
import kotlinx.coroutines.launch
import org.w3c.fetch.RequestInit
import kotlin.js.json

public fun RenderContext.homePage() {
    coroutineScope.launch {
        val robVersion = storeOf(getROneBotVersion())
        val gradleVersion = storeOf(getGradleVersion())
        val kotlinVersion = storeOf(getKotlinVersion())
        val groupId = storeOf("com.example.project")
        val plugins = storeOf("Shadow,Java,KotlinJvm,KotlinMultiplatform")
        val projectName = storeOf("TestProject")
        val packageName = storeOf("com.example.test")
        val language = storeOf("Kotlin")
        val isMultiplatform = storeOf(true)
        val platforms = storeOf("Jvm,LinuxX64,MingwX64,LinuxArm64,MacOSArm64")
        val javaVersion = storeOf("17")
        val protocol = storeOf("milky")

        div("container") {
            div("field") {
                label("label") {
                    +"项目名"
                }
                div("control") {
                    input("input") {
                        type("text")
                        value("Example")
                        placeholder("输入项目名称")
                        changes.values() handledBy { projectName.update }
                    }
                }
            }
            div("field") {
                label("label"){
                    +"Group ID"
                    div("control") {
                        input("input") {
                            type("text")
                            value("com.example")
                            placeholder("输入Group ID名称")
                            changes.values() handledBy { groupId.update }
                        }
                    }
                }
            }
            div("field") {
                label("label") {
                    +"Kotlin版本"

                }
            }
        }

        div("field") {
            button("button is-primary") {
                +"Submit"
                clicks handledBy {
                    val data = GeneratorProperty(
                        groupId = groupId.current,
                        plugins = plugins.current.split(",").map { GradlePlugins.cast(it.trim()) },
                        projectName = projectName.current,
                        packageName = packageName.current,
                        robVersion = robVersion.current,
                        gradleVersion = gradleVersion.current,
                        kotlinVersion = kotlinVersion.current,
                        language = Language.fromName(language.current),
                        isMultiplatform = isMultiplatform.current,
                        platforms = platforms.current.split(",").map { ROneBotPlatform.fromString(it.trim()) },
                        javaVersion = javaVersion.current,
                        protocol = protocol.current
                    )
                    coroutineScope.launch {
                        window.fetch(
                            "$backend/api/generate", RequestInit(
                                method = "POST",
                                body = data.toJson(),
                                headers = json("Content-Type" to "application/json")
                            )
                        )
                    }
                }
            }
        }
    }
}