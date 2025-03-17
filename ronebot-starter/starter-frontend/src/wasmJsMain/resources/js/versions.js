let kotlin_version;

function getLatestKotlinVersion() {
    return JSON.parse(JSON.stringify(kotlin_version)).tag_name.replace("v", "")
}

fetch('https://api.github.com/repos/JetBrains/kotlin/releases/latest')
    .then(response => response.json())
    .then(data => {
        kotlin_version = data;
    }).catch(error => console.error('Error:', error));