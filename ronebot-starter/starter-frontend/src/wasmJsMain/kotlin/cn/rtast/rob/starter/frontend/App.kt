/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/11
 */

@file:Suppress("unused", "FunctionName")

package cn.rtast.rob.starter.frontend

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import cn.rtast.rob.starter.frontend.api.fetchLatestROBVersion
import cn.rtast.rob.starter.frontend.api.getLatestGradleVersion
import cn.rtast.rob.starter.frontend.api.getLatestKotlinVersion
import cn.rtast.rob.starter.frontend.api.submitFormData
import cn.rtast.rob.starter.frontend.composable.Chip
import cn.rtast.rob.starter.frontend.composable.DividerSplit
import cn.rtast.rob.starter.frontend.composable.Footer
import cn.rtast.rob.starter.frontend.enums.ExtraFeature
import cn.rtast.rob.starter.frontend.enums.PlatformType
import cn.rtast.rob.starter.frontend.resources.Res
import cn.rtast.rob.starter.frontend.resources.github
import cn.rtast.rob.starter.frontend.util.Config
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.w3c.dom.HTMLAnchorElement
import org.w3c.dom.url.URL
import org.w3c.files.Blob


@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class)
@Composable
public fun App(config: Config) {
    var gradleVersion by remember { mutableStateOf(TextFieldValue("8.13")) }
    var projectName by remember { mutableStateOf(TextFieldValue("ExampleROBProject")) }
    var group by remember { mutableStateOf(TextFieldValue("com.example.rob")) }
    var kotlinVersion by remember { mutableStateOf(TextFieldValue("2.1.10")) }
    var robVersion by remember { mutableStateOf(TextFieldValue("2.8.4")) }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf<Boolean>(false) }
    val scope = rememberCoroutineScope()
    var selectedProjectType by remember { mutableStateOf("OneBot11") }
    val projectType = PlatformType.entries
    var selectedExtraFeatures = remember { mutableStateOf(mutableSetOf<ExtraFeature>()) }
    LaunchedEffect(Unit) {
        robVersion = TextFieldValue(fetchLatestROBVersion())
        kotlinVersion = TextFieldValue(getLatestKotlinVersion())
        gradleVersion = TextFieldValue(getLatestGradleVersion())
    }
    fun submitForm() {
        if (projectName.text.isBlank() ||
            group.text.isBlank() ||
            kotlinVersion.text.isBlank() ||
            robVersion.text.isBlank()
        ) {
            errorMessage = "你有未填写的选项!"
            return
        }
        errorMessage = ""
        val formData = mapOf(
            "projectName" to projectName.text,
            "group" to group.text,
            "robVersion" to robVersion.text,
            "kotlinVersion" to kotlinVersion.text,
            "type" to selectedProjectType.split("(").first(),
            "features" to selectedExtraFeatures.value.joinToString(",") { it.featureString },
            "gradleVersion" to gradleVersion.text
        )
        scope.launch {
            isLoading = true
            submitFormData(config.backend, formData) {
                it.onload = { _ ->
                    if (it.status.toInt() == 200) {
                        val blob = it.response as Blob
                        val downloadUrl = URL.createObjectURL(blob)
                        val a = document.createElement("a") as HTMLAnchorElement
                        a.href = downloadUrl
                        a.download = "${projectName.text}.zip"
                        a.click()
                        URL.revokeObjectURL(downloadUrl)
                        isLoading = false
                    }
                }
            }
        }
    }
    Box(
        modifier = Modifier.padding(2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "ROneBot 模板项目生成器",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(top = 16.dp)
            )
            DividerSplit()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = 8.dp,
                    modifier = Modifier
                        .padding(8.dp)
                        .widthIn(max = 480.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(24.dp)
                    ) {
                        Text("创建新项目", style = MaterialTheme.typography.h5)
                        DividerSplit()
                        TextField(
                            value = projectName,
                            onValueChange = { projectName = it },
                            label = { Text("项目名") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color(0x09ACD)
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = group,
                            onValueChange = { group = it },
                            label = { Text("Group ID") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color(0x009ACD)
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = kotlinVersion,
                            onValueChange = { kotlinVersion = it },
                            label = { Text("Kotlin 版本") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color(0x009ACD)
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = robVersion,
                            onValueChange = { robVersion = it },
                            label = { Text("ROneBot 版本") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color(0x009ACD)
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = gradleVersion,
                            onValueChange = { gradleVersion = it },
                            label = { Text("Gradle 版本") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color(0x009ACD)
                            )
                        )
                    }
                }
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = 8.dp,
                    modifier = Modifier
                        .padding(8.dp)
                        .widthIn(max = 480.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(24.dp)
                    ) {
                        Text("额外选项", style = MaterialTheme.typography.h5)
                        DividerSplit()
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            ExtraFeature.entries.forEach { item ->
                                val isSelected = item in selectedExtraFeatures.value
                                Chip(
                                    item = item,
                                    isSelected = isSelected,
                                    onSelectionChanged = { selected ->
                                        selectedExtraFeatures.value = (if (selected) {
                                            selectedExtraFeatures.value + item
                                        } else {
                                            selectedExtraFeatures.value - item
                                        }).toMutableSet()
                                    }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = "已选择: ${selectedExtraFeatures.value.joinToString(", ") { it.featureName }}")
                        DividerSplit()
                        Text("选择平台")
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            projectType.forEach { option ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { selectedProjectType = option.platformString }
                                ) {
                                    RadioButton(
                                        selected = (selectedProjectType == option.platformString),
                                        onClick = { selectedProjectType = option.platformString },
                                        colors = RadioButtonDefaults.colors(
                                            selectedColor = Color.Gray
                                        )
                                    )
                                    Text(option.platformName, modifier = Modifier.padding(start = 4.dp))
                                }
                            }
                        }
                        DividerSplit()
                        Text("关于", style = MaterialTheme.typography.h5)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("作者: RTAkland", modifier = Modifier.clickable {
                            window.open("https://github.com/RTAkland", "_blank")
                        })
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("项目: ROneBot", modifier = Modifier.clickable {
                            window.open("https://github.com/RTAkland/ROneBot", "_blank")
                        })
                    }
                }
            }
            Spacer(modifier = Modifier.height(36.dp))
            if (isLoading) {
                LinearProgressIndicator(color = Color.Blue)
            } else {
                Button(
                    onClick = { submitForm() },
                    modifier = Modifier.fillMaxWidth(0.3f),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0x5CACEE))
                ) {
                    Text("开始生成项目")
                }
            }
            DividerSplit()
            Footer()
        }
        FloatingActionButton(
            onClick = { window.open("https://github.com/RTAkland/ROneBot") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 40.dp, bottom = 65.dp),
            backgroundColor = Color(0x5CACEE),
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(0.dp)
        ) {
            Image(
                painter = painterResource(Res.drawable.github),
                contentDescription = "Github图标",
                modifier = Modifier.width(45.dp)
            )
        }
    }
}