repositories {
    maven("https://repo.maven.rtast.cn/releases/")
}

dependencies {
    api(libs.java.websocket)
    api(project(":ronebot-common"))

    testImplementation("cn.rtast:ronebot-permission:2.6.10")
}