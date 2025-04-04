@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

kotlin {
    explicitApi()
    wasmJs {
        browser()
    }
    linuxX64()
    linuxArm64()
    mingwX64()
}

tasks.withType<AbstractPublishToMaven>().configureEach {
    onlyIf { false }
}