plugins {
    kotlin("jvm") version "2.0.0"
}

group = "cn.rtast"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.javaWebsocket)
    implementation(libs.gson)
}
