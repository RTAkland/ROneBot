@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

tasks.withType<AbstractPublishToMaven>().configureEach {
    onlyIf { false }
}

kotlin {
//    wasmJs {
//        browser()
//    }
    jvm()
}