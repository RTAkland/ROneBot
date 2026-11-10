rootProject.name = "ROneBot"

include(":ronebot-common", ":ronebot-onebot-v11")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://repo.rtast.cn/packages/")
        mavenLocal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        mavenLocal()
        maven("https://repo.rtast.cn/packages/")
    }
}
