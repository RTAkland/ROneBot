let kotlin_version;

function getLatestKotlinVersion() {
    return JSON.parse(JSON.stringify(kotlin_version)).tag_name.replace("v", "")
}

fetch('https://api.github.com/repos/JetBrains/kotlin/releases/latest')
    .then(response => response.json())
    .then(data => {
        kotlin_version = data;
    }).catch(error => console.error('Error:', error));

let gradle_version;

function getLatestGradleVersion() {
    return JSON.parse(JSON.stringify(gradle_version)).name
}

fetch('https://api.github.com/repos/gradle/gradle/releases/latest')
    .then(response => response.json())
    .then(data => {
        gradle_version = data;
    }).catch(error => console.error('Error:', error));