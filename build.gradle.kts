import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin)
    id("maven-publish")
}

val libVersion: String by project

group = "cn.rtast"
version = libVersion

repositories {
    mavenCentral()
}

dependencies {
    api(libs.gson)
    api(libs.java.websocket)
    api(libs.kotlin.coroutines)
}

val sourceJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

artifacts {
    archives(sourceJar)
}

tasks.compileKotlin {
    compilerOptions.jvmTarget = JvmTarget.JVM_11
}

tasks.compileJava {
    sourceCompatibility = "11"
    targetCompatibility = "11"
}

tasks.jar {
    from("LICENSE") {
        rename { "ROneBot-LICENSE-Apache2.0" }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(sourceJar)
        }
    }

    repositories {
        maven {
            url = uri("https://repo.rtast.cn/api/v4/projects/33/packages/maven")
            credentials {
                username = "RTAkland"
                password = System.getenv("TOKEN")
            }
        }
    }
}
