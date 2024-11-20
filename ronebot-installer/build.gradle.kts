plugins {
    application
}

dependencies {
    implementation(project(":ronebot-common-ext"))
}

application {
    mainClass = "cn.rtast.rob.installer.MainKt"
}

