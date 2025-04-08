plugins {
    id("java")
    id("application")
    id("com.gradleup.shadow") version "8.3.0"
}

val appVersion: String by extra

group = "{{GROUP_ID}}"
version = appVersion

repositories {
    mavenCentral()
    // 补药移除这里的仓库否则会导致无法下载依赖
    maven("https://repo.maven.rtast.cn/releases")
}

dependencies {
    implementation("cn.rtast.rob:{{PLATFORM}}:{{ROB_VERSION}}"){{EXTRA_FEATURES}}
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

application {
    mainClass = "{{MAIN_CLASS}}"
}

tasks.compileJava {
    sourceCompatibility = "11"
    targetCompatibility = "11"
}