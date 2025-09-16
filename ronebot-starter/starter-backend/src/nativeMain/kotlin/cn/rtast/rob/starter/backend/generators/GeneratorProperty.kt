/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 9/16/25, 6:32 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.starter.backend.generators

import cn.rtast.rob.starter.common.GradlePlugins
import cn.rtast.rob.starter.common.Language
import cn.rtast.rob.starter.common.ROneBotPlatform
import kotlinx.serialization.Serializable

@Serializable
data class GeneratorProperty(
    val groupId: String,
    val plugins: List<GradlePlugins>,
    val projectName: String,
    val packageName: String,
    val robVersion: String,
    val gradleVersion: String,
    val kotlinVersion: String,
    val language: Language,
    val isMultiplatform: Boolean,
    val platforms: List<ROneBotPlatform>,
    val javaVersion: String? = null,
)