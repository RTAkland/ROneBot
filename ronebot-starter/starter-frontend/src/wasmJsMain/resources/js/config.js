let jsonData;

function loadConfigExternal() {
    return JSON.stringify(jsonData);
}

fetch('config/config.json')
    .then(response => response.json())
    .then(data => {
        jsonData = data;
    }).catch(error => console.error('Error:', error));
