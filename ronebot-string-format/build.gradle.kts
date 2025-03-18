import org.jetbrains.kotlin.gradle.dsl.JvmTarget

dependencies {
    implementation(libs.kmarkdown)
}

kotlin {
    explicitApi()
    compilerOptions.jvmTarget = JvmTarget.JVM_11
}
