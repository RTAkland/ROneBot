/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/11
 */


package cn.rtast.rob.starter.frontend

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.await
import org.khronos.webgl.ArrayBuffer
import org.w3c.fetch.Response

lateinit var zpixFontFamily: FontFamily


@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        val fontFamilyResolver = LocalFontFamilyResolver.current
        val fontLoad = remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            val fontFamily = FontFamily(
                listOf(
                    Font(
                        "ZPIX", window.fetch("http://ide-image.test.upcdn.net/zpix.ttf")
                            .await<Response>().arrayBuffer()
                            .await<ArrayBuffer>().toByteArray()
                    )
                )
            )
            fontFamilyResolver.preload(fontFamily)
            zpixFontFamily = fontFamily
            fontLoad.value = true
        }
        if (fontLoad.value) {
            App()
        }
    }
}