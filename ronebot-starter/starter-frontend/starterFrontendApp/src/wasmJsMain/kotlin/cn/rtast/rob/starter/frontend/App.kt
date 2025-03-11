/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/11
 */

@file:Suppress("unused", "FunctionName")

package cn.rtast.rob.starter.frontend

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

var isLoading = false

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun App() {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize().background(color = Color(0x80FFFFFF))) {
            var projectName by remember { mutableStateOf(TextFieldValue("ExampleROBProject")) }
            var group by remember { mutableStateOf(TextFieldValue("com.example.rob")) }
            var gradleVersion by remember { mutableStateOf(TextFieldValue("8.11")) }
            var kotlinVersion by remember { mutableStateOf(TextFieldValue("2.1.0")) }
            var robVersion by remember { mutableStateOf<String?>(null) }
            var errorMessage by remember { mutableStateOf("") }
            var versions by remember { mutableStateOf<List<String>>(emptyList()) }
            val scope = rememberCoroutineScope()
            LaunchedEffect(Unit) {
                val version = fetchLatestVersion()
                versions = listOf(version)
            }

            fun submitForm() {
                if (projectName.text.isBlank() ||
                    group.text.isBlank() ||
                    gradleVersion.text.isBlank() ||
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
                    "gradleVersion" to gradleVersion.text,
                    "kotlinVersion" to kotlinVersion.text
                )

                scope.launch {
                    isLoading = true
                    submitFormData(formData)
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "ROneBot 项目模板生成器",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = 8.dp,
                    modifier = Modifier.fillMaxWidth(0.6f)
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
                            value = gradleVersion,
                            onValueChange = { gradleVersion = it },
                            label = { Text("Gradle版本") },
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
                        Text("选择ROB版本", style = MaterialTheme.typography.body1)
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
                        if (errorMessage.isNotBlank()) {
                            Text(text = errorMessage, color = Color.Red)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        if (isLoading) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                        } else {
                            Button(
                                onClick = { submitForm() },
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .align(Alignment.CenterHorizontally),
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0x5CACEE))
                            ) {
                                Text("开始生成项目")
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Footer()
            }
        }
    }
}

@Composable
fun Footer() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "© 2024-present RTAkland & xiaoman1221. All Rights Reserved.",
            style = MaterialTheme.typography.body2,
            color = Color.Gray
        )
    }
}