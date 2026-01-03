import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.suspend.transformer)
}

repositories {
    maven("https://libraries.minecraft.net")
}

kotlin {
    withSourcesJar()
    explicitApi()
    jvm {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
            freeCompilerArgs.apply {
                add("-Xjvm-default=all")
            }
        }
    }
    mingwX64()
    linuxX64()
    linuxArm64()
    macosArm64()
    macosX64()
    js(IR) { nodejs { binaries.executable() } }

    compilerOptions {
        freeCompilerArgs.apply {
            add("-Xexpect-actual-classes")
            add("-Xcontext-parameters")
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":ronebot-common"))
        }

        jvmMain.dependencies {
            api(libs.java.websocket)
        }

        nativeMain.dependencies {
            api(libs.ktor.server.websockets)
            api(libs.ktor.client.websockets)
        }

        appleMain.dependencies {
            api(libs.ktor.client.darwin)
        }

        linuxMain.dependencies {
            api(libs.ktor.client.curl)
        }

        mingwMain.dependencies {
            api(libs.ktor.client.winhttp)
        }

        jsMain.dependencies {
            api("cn.rtast.kotlin-cfworker:core:1.0.3")
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

suspendTransformPlugin {
    enabled = true
    includeRuntime = true
    transformers {
        useJvmDefault()
    }
    runtimeDependency {
        configurationName = "api"
    }
}

val wranglerRunDir: Provider<Directory> = layout.buildDirectory.dir("wrangler-run")
tasks.register<Copy>("prepareWranglerRun") {
    group = "wrangler"
    dependsOn("compileDevelopmentExecutableKotlinJs")
    val buildOutputDir = layout.buildDirectory.dir("compileSync/js/main/developmentExecutable/kotlin")
    from(buildOutputDir)
    into(wranglerRunDir)
}

val wranglerDev by tasks.registering(Exec::class) {
    group = "wrangler"
    workingDir = layout.buildDirectory.dir("wrangler-run").get().asFile.apply { mkdirs() }
    doFirst {
        val sourceDir = project.layout.projectDirectory
        mapOf(
            sourceDir.file("wrangler.toml").asFile to File(workingDir, "wrangler.toml"),
            sourceDir.file(".dev.vars").asFile to File(workingDir, ".dev.vars"),
        ).forEach { (s, d) -> s.copyTo(d, overwrite = true) }
    }
    commandLine(
        if (System.getProperty("os.name").lowercase().contains("windows")) listOf(
            "cmd", "/c", "wrangler dev --port 7071"
        )
        else listOf("sh", "-c", "wrangler dev --port 7071")
    )
    standardInput = System.`in`
    isIgnoreExitValue = false
}

val wranglerDeployDir: Provider<Directory> = layout.buildDirectory.dir("wrangler-deploy")
    .apply { get().asFile.deleteRecursively() }
val prepareProductionDeploy by tasks.registering(Copy::class) {
    group = "wrangler"
    dependsOn("compileProductionExecutableKotlinJs")
    val buildOutputDir = layout.buildDirectory.dir("compileSync/js/main/productionExecutable/kotlin")
    from(buildOutputDir) { exclude("*.map") }
    into(wranglerDeployDir)
    from(layout.projectDirectory.file("wrangler.toml"))
    into(wranglerDeployDir)
}

val wranglerDeploy by tasks.registering(Exec::class) {
    group = "wrangler"
    dependsOn(prepareProductionDeploy)
    workingDir = layout.buildDirectory.dir("wrangler-deploy").get().asFile.apply { mkdirs() }
    commandLine(
        if (System.getProperty("os.name").lowercase().contains("windows")) listOf("cmd", "/c", "wrangler deploy")
        else listOf("sh", "-c", "wrangler deploy")
    )
    standardInput = System.`in`
}