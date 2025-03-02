repositories {
    maven("https://maven.rtast.cn/snapshots")
}

dependencies {
    api(libs.java.websocket)
    api(project(":ronebot-common"))

    testImplementation(project(":ronebot-permission"))
    testImplementation("cn.rtast:ronebot-onebot-v11:2.8.4")
}