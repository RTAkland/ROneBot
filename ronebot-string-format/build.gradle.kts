import org.jetbrains.kotlin.gradle.dsl.JvmTarget

kotlin {
    withSourcesJar()
    explicitApi()
    jvm {
        compilerOptions.jvmTarget = JvmTarget.JVM_11
    }
    mingwX64()
    linuxX64()
    js(IR) {
        nodejs()
    }

    sourceSets {
        commonMain {
            dependencies {
                api(libs.kmarkdown)
            }
        }
    }
}