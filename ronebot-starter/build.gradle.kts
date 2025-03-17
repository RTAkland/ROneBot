tasks.withType<AbstractPublishToMaven>().configureEach {
    onlyIf { false }
}