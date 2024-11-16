plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "ROneBot"
include(":ronebot-common")
include(":ronebot-common-ext")
include(":ronebot-onebot-v11")
include(":ronebot-satori")
include(":ronebot-kritor")
include(":ronebot-qqbot-webhook")
include(":ronebot-qqbot-legacy")
include("ronebot-silk-codec-ext")
