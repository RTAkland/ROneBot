kotlin {
    jvm()
}

tasks.withType<AbstractPublishToMaven>().configureEach {
    onlyIf { false }
}