@file:Suppress("UnstableApiUsage")

rootProject.name = "ROneBot"

listOf(
    ":ronebot-common",
    ":ronebot-common-http",
    ":ronebot-onebot-v11",
    ":ronebot-qqbot-webhook",
    ":ronebot-permission",
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
