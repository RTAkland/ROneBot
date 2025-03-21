import org.jetbrains.kotlin.gradle.dsl.JvmTarget

//dependencies {
//    implementation(libs.kmarkdown)
//}
//
//kotlin {
//    explicitApi()
//    compilerOptions.jvmTarget = JvmTarget.JVM_11
//}

kotlin {
    withSourcesJar()
    explicitApi()
    jvm()
    mingwX64()
    linuxX64()
}