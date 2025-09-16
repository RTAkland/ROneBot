@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

kotlin {
    explicitApi()
    linuxX64()
    linuxArm64()
    mingwX64()
    js(IR) { browser() }

    sourceSets {
        commonMain.dependencies {
            api(libs.kotlin.serialization)
        }
    }
}

tasks.withType<AbstractPublishToMaven>().configureEach {
    onlyIf { false }
}