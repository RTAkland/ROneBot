@file:Suppress("UnstableApiUsage")

rootProject.name = "ROneBot"

listOf(
    ":ronebot-common",
    ":ronebot-onebot-v11",
    ":ronebot-starter:starter-backend",
    ":ronebot-starter:starter-frontend",
    ":ronebot-starter:starter-common",
    ":ronebot-utils:ronebot-concurrency",
    ":ronebot-utils:ronebot-bytearray",
    ":ronebot-milky"
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
