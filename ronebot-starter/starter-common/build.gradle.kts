@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

kotlin {
    wasmJs {
        browser()
    }
    jvm {
        compilerOptions.jvmTarget = JvmTarget.JVM_11
    }
}