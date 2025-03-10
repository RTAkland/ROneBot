@file:Suppress("UnstableApiUsage")

rootProject.name = "ROneBot"

listOf(
    ":ronebot-common",
    ":ronebot-common-ext",
    ":ronebot-onebot-v11",
    ":ronebot-satori",
    ":ronebot-qqbot-webhook",
    ":ronebot-permission",
    ":ronebot-string-format",
    ":ronebot-kook-common",
    ":ronebot-kook-ws",
    ":ronebot-kook-webhook",
    ":ronebot-kook-webhook-url-validator",
    ":ronebot-starter:starter-backend",
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
