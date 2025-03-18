/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/11
 */


package cn.rtast.rob.starter.frontend

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.window.ComposeViewport
import cn.rtast.rob.starter.frontend.util.Config
import cn.rtast.rob.starter.frontend.util.loadConfig
import cn.rtast.rob.starter.frontend.util.toByteArray
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.await
import org.khronos.webgl.ArrayBuffer
import org.w3c.fetch.Response

@OptIn(ExperimentalComposeUiApi::class)
public fun main() {
    ComposeViewport(document.body!!) {
        val fontFamilyResolver = LocalFontFamilyResolver.current
        val fontLoad = remember { mutableStateOf(false) }
        var config by remember { mutableStateOf<Config?>(null) }
        LaunchedEffect(Unit) {
            config = loadConfig()
            val fontFamily = FontFamily(
                listOf(
                    Font(
                        "ROBCUSTOMFONT",
                        window.fetch(config!!.font)
                            .await<Response>().arrayBuffer()
                            .await<ArrayBuffer>().toByteArray()
                    )
                )
            )
            fontFamilyResolver.preload(fontFamily)
            fontLoad.value = true
        }
        if (fontLoad.value && config != null) {
            App(config!!)
        }
    }
}