/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/8 14:21
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:Suppress("FUNCTIONNAME")

package cn.rtast.rob.starter.frontend.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import cn.rtast.rob.starter.common.ROneBotTarget

@Composable
public fun TargetChip(
    item: ROneBotTarget,
    isSelected: Boolean,
    onSelectionChanged: (Boolean) -> Unit
) {
    val leadingIcon: @Composable () -> Unit = {
        if (isSelected) {
            Icon(Icons.Default.Check, contentDescription = null, Modifier.width(18.dp))
        }
    }
    Card(
        backgroundColor = if (isSelected) Color.Blue else MaterialTheme.colors.surface,
        contentColor = if (isSelected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface,
        elevation = 4.dp,
        modifier = Modifier.clickable { onSelectionChanged(!isSelected) }.padding(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            leadingIcon()
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = item.targetDisplayName)
        }
    }
}