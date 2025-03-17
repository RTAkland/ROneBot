/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/11
 */

@file:Suppress("unused", "FunctionName")

package cn.rtast.rob.starter.frontend

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import cn.rtast.rob.starter.frontend.api.fetchLatestVersion
import cn.rtast.rob.starter.frontend.api.submitFormData
import cn.rtast.rob.starter.frontend.composable.Chip
import cn.rtast.rob.starter.frontend.composable.Footer
import cn.rtast.rob.starter.frontend.enums.ExtraFeature
import cn.rtast.rob.starter.frontend.enums.PlatformType
import cn.rtast.rob.starter.frontend.util.Config
import cn.rtast.rob.starter.frontend.util.DEFAULT_CONFIG
import cn.rtast.rob.starter.frontend.util.loadConfig
import kotlinx.browser.document
import kotlinx.coroutines.launch
import org.w3c.dom.HTMLAnchorElement
import org.w3c.dom.url.URL
import org.w3c.files.Blob


@OptIn(ExperimentalLayoutApi::class)
@Composable
public fun App() {
    var config by remember { mutableStateOf<Config>(DEFAULT_CONFIG) }
    var projectName by remember { mutableStateOf(TextFieldValue("ExampleROBProject")) }
    var group by remember { mutableStateOf(TextFieldValue("com.example.rob")) }
    var kotlinVersion by remember { mutableStateOf(TextFieldValue("2.1.10")) }
    var robVersion by remember { mutableStateOf<String?>(null) }
    var errorMessage by remember { mutableStateOf("") }
    var versions by remember { mutableStateOf<List<String>>(emptyList()) }
    var isLoading by remember { mutableStateOf<Boolean>(false) }
    val scope = rememberCoroutineScope()
    var selectedProjectType by remember { mutableStateOf("OneBot11") }
    val projectType = PlatformType.entries
    var selectedExtraFeatures = remember { mutableStateOf(mutableSetOf<ExtraFeature>()) }
    LaunchedEffect(Unit) {
        config = loadConfig()
        versions = listOf(fetchLatestVersion())
//        kotlinVersion = TextFieldValue(getLatestKotlinVersion())
    }
    Box(modifier = Modifier.fillMaxSize().background(color = Color(0x80FFFFFF))) {
        fun submitForm() {
            if (projectName.text.isBlank() ||
                group.text.isBlank() ||
                kotlinVersion.text.isBlank() ||
                robVersion.isNullOrBlank()
            ) {
                errorMessage = "你有未填写的选项!"
                return
            }
            errorMessage = ""
            val formData = mapOf(
                "projectName" to projectName.text,
                "group" to group.text,
                "robVersion" to (robVersion ?: ""),
                "kotlinVersion" to kotlinVersion.text,
                "type" to selectedProjectType.split("(").first(),
                "features" to selectedExtraFeatures.value.joinToString(",") { it.featureString }
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
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = 8.dp,
            modifier = Modifier.padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "ROneBot 模板项目生成器",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        elevation = 8.dp,
                        modifier = Modifier.weight(1f).padding(8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .padding(24.dp)
                        ) {
                            Text("创建新项目", style = MaterialTheme.typography.h5)
                            Spacer(modifier = Modifier.height(16.dp))
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
                                label = { Text("Kotlin版本") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                colors = TextFieldDefaults.textFieldColors(
                                    focusedIndicatorColor = Color(0x009ACD)
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            if (versions.isEmpty()) {
                                CircularProgressIndicator()
                            } else {
                                robVersion = versions.first()
                                TextField(
                                    value = versions.first(),
                                    onValueChange = { robVersion = it },
                                    label = { Text("ROneBot版本号") },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    colors = TextFieldDefaults.textFieldColors(
                                        focusedIndicatorColor = Color(0x009ACD)
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
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
                        }
                    }
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        elevation = 8.dp,
                        modifier = Modifier.weight(1f).padding(8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .padding(24.dp)
                        ) {
                            Text("额外选项", style = MaterialTheme.typography.h5)
                            Spacer(modifier = Modifier.height(16.dp))
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
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "已选择: ${selectedExtraFeatures.value.joinToString(", ") { it.featureName }}")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                if (errorMessage.isNotBlank()) {
                    Text(text = errorMessage, color = Color.Red)
                }
                Spacer(modifier = Modifier.height(10.dp))
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else {
                    Button(
                        onClick = { submitForm() },
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .align(Alignment.CenterHorizontally),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0x5CACEE))
                    ) {
                        Text("开始生成项目")
                    }
                }
                Spacer(modifier = Modifier.height(3.dp).align(Alignment.CenterHorizontally))
                Footer()
            }
        }
    }
}