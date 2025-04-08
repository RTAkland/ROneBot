kotlin.targets.named<KotlinJvmTarget>("jvm") {
    compilations.named("main") {
        tasks {
            val shadowJar = register<ShadowJar>("jvmShadowJar") {
                group = "build"
                from(output)
                configurations = listOf(runtimeDependencyFiles)
                archiveAppendix.set("jvm")
                archiveClassifier.set("all")
                manifest {
                    attributes("Main-Class" to "{{MAIN_CLASS}}")
                }
                mergeServiceFiles()
            }
            getByName("jvmJar") {
                finalizedBy(shadowJar)
            }
        }
    }
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.WARN
}