/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/18
 */

@file:Suppress("FunctionName")

package cn.rtast.rob.starter.frontend.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cn.rtast.rob.starter.frontend.enums.ExtraFeature

@Composable
public fun Chip(
    item: ExtraFeature,
    isSelected: Boolean,
    onSelectionChanged: (Boolean) -> Unit
) {
    Card(
        backgroundColor = if (isSelected) Color.Gray else MaterialTheme.colors.surface,
        contentColor = if (isSelected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface,
        elevation = 4.dp,
        modifier = Modifier
            .clickable { onSelectionChanged(!isSelected) }
            .padding(4.dp)
    ) {
        Text(
            text = item.featureName,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}