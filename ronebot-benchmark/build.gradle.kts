import org.jetbrains.kotlin.gradle.dsl.JvmTarget

kotlin {
    explicitApi()
    compilerOptions.jvmTarget = JvmTarget.JVM_11
}

dependencies {
    implementation(project(":ronebot-onebot-v11"))
}

tasks.withType<AbstractPublishToMaven>().configureEach {
    onlyIf { false }
}