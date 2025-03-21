/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/19
 */

@file:Suppress("FunctionName")

package cn.rtast.rob.starter.frontend.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
public fun DividerSplit(top: Int = 18, bottom: Int = 18) {
    Spacer(modifier = Modifier.height(top.dp))
    Divider()
    Spacer(modifier = Modifier.height(bottom.dp))
}