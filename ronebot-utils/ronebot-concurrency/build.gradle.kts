import org.jetbrains.kotlin.gradle.dsl.JvmTarget

kotlin {
    withSourcesJar()
    explicitApi()
    jvm { compilerOptions.jvmTarget = JvmTarget.JVM_11 }
    mingwX64()
    linuxX64()
    linuxArm64()
    macosArm64()
    macosX64()
    js(IR) {
        useEsModules()
        browser()
    }

    compilerOptions {
        freeCompilerArgs.apply {
            add("-Xexpect-actual-classes")
            add("-Xcontext-parameters")
        }
    }
    sourceSets {
        commonMain {
            dependencies {
                api(libs.kotlinx.coroutines)
            }
        }
    }
}