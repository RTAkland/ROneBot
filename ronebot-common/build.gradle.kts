plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.serialization)
}

kotlin {
    val hostOs = System.getProperty("os.name")
    val isArm64 = System.getProperty("os.arch") == "aarch64"
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Linux" && !isArm64 -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }
}
//    api(libs.gson)
//    api(libs.kotlinx.coroutines)
//    api(libs.kotlin.stdlib)
//    api(libs.slf4j.api)
//    api(libs.logback.classic)
////    api(libs.kotlin.reflect)
//    api(libs.brigadier)
