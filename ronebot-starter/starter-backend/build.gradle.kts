plugins {
    alias(libs.plugins.shadow)
    application
}

repositories {
    maven("https://repo.maven.rtast.cn/releases/")
}

dependencies {
    implementation(project(":ronebot-common-ext"))
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.rtast.util.string)
}

tasks.withType<AbstractPublishToMaven>().configureEach {
    onlyIf { false }
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.shadowJar {
    archiveFileName = "app.jar"
}

application {
    mainClass = "cn.rtast.rob.starter.backend.MainKt"
}