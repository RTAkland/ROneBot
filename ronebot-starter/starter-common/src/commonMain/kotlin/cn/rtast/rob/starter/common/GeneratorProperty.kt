/*
 * Copyright © 2025 RTAkland
 * Date: 9/16/25, 10:56 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.starter.common

import kotlinx.serialization.Serializable

@Serializable
public data class GeneratorProperty(
    val protocol: String,
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