dependencies {
    implementation(project(":ronebot-onebot-v11"))
}

tasks.withType<AbstractPublishToMaven>().configureEach {
    onlyIf { false }
}