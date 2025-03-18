/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/18
 */

@file:Suppress("FunctionName")

package cn.rtast.rob.starter.frontend.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.onClick
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle

@OptIn(ExperimentalFoundationApi::class)
@Composable
public fun Footer() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = buildAnnotatedString {
                append("© 2024-present ")
                withStyle(style = SpanStyle(color = Color.Gray, textDecoration = TextDecoration.Underline)) {
                    withLink(LinkAnnotation.Url("https://github.com/RTAkland")) {
                        append("RTAkland")
                    }
                }
                append(" & ")
                withStyle(style = SpanStyle(color = Color.Gray, textDecoration = TextDecoration.Underline)) {
                    withLink(LinkAnnotation.Url("https://github.com/xiaoman1221")) {
                        append("xiaoman1221")
                    }
                }
                append(". All Rights Reserved.")
            },
            style = MaterialTheme.typography.body2,
            color = Color.Gray
        )
    }
}