@file:Suppress("UnstableApiUsage")

rootProject.name = "ROneBot"

listOf(
    ":ronebot-common",
    ":ronebot-common-http",
    ":ronebot-onebot-v11",
    ":ronebot-onebot-v11-okio",
    ":ronebot-qqbot-webhook",
    ":ronebot-string-format",
    ":ronebot-starter:starter-backend",
    ":ronebot-starter:starter-frontend",
    ":ronebot-starter:starter-common",
    ":ronebot-utils:ronebot-concurrency",
    ":ronebot-utils:ronebot-uuid",
    ":ronebot-utils:ronebot-file",
    ":ronebot-utils:ronebot-bytearray",
    ":ronebot-messagedb",
).forEach {
    include(it)
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven("https://repo.maven.rtast.cn/releases/")
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}
