plugins {
    id("application")
    alias(libs.plugins.shadow)
}

dependencies {
    implementation(project(":ronebot-onebot-v11"))
}

tasks.withType<AbstractPublishToMaven>().configureEach {
    onlyIf { false }
}

application {
    mainClass = "cn.rtast.rob.example.MainKt"
}
