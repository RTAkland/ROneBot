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
