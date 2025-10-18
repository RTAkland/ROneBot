@file:Suppress("UnstableApiUsage")

rootProject.name = "ROneBot"

listOf(
    ":ronebot-common",
    ":ronebot-onebot-v11",
    ":ronebot-utils:ronebot-concurrency",
    ":ronebot-utils:ronebot-bytearray",
    ":ronebot-milky",
    ":ronebot-stream-api",
).forEach {
    include(it)
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://repo.maven.rtast.cn/releases/")
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}
