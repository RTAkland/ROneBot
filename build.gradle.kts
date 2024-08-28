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
    api(libs.javaWebsocket)
    api(libs.gson)
    api(libs.kotlinCoroutines)
}

tasks.register<Jar>("sourceJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

artifacts {
    archives(tasks.named("sourceJar"))
}

tasks.compileKotlin {
    compilerOptions.jvmTarget = JvmTarget.JVM_11
}

tasks.compileJava {
    sourceCompatibility = "11"
    targetCompatibility = "11"
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(tasks["sourceJar"])
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
