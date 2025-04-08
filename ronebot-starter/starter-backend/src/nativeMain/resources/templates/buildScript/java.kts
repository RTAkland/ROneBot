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
    maven("https://repo.maven.rtast.cn/releases")
    maven("https://repo.maven.rtast.cn/snapshots")
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