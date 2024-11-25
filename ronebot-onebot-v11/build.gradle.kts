plugins {
    application
}

dependencies {
    api(libs.java.websocket)
    api(project(":ronebot-common"))
}

application {
    mainClass = "cn.rtast.rob.ROneBotFactoryKt"
}