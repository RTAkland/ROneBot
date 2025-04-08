/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/18
 */

@file:Suppress("FunctionName")
@file:OptIn(ExperimentalFoundationApi::class)

package cn.rtast.rob.starter.frontend.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cn.rtast.rob.starter.common.ExtraFeature

@Composable
public fun Chip(
    item: ExtraFeature,
    isSelected: Boolean,
    onSelectionChanged: (Boolean) -> Unit,
    enabled: Boolean,
) {
    Box {
        Card(
            backgroundColor = if (!enabled) Color.LightGray else if (isSelected) Color.Blue else MaterialTheme.colors.surface,
            contentColor = if (!enabled) Color.DarkGray else if (isSelected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface,
            elevation = 4.dp,
            modifier = Modifier
                .then(if (enabled) Modifier.clickable { onSelectionChanged(!isSelected) } else Modifier)
                .padding(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                if (isSelected && enabled) {
                    Icon(Icons.Default.Check, contentDescription = null, Modifier.width(18.dp))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = item.featureName)
            }
        }
    }
}
