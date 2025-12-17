/*
 * Copyright © 2025 RTAkland
 * Date: 2025/12/16 21:25
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

plugins {
    kotlin("jvm") version "2.2.21"
    id("com.microsoft.azure.azurefunctions") version "1.16.1"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.microsoft.azure.functions:azure-functions-java-library:3.2.2")
    implementation("cn.rtast.rob:core:3.3.0")
}