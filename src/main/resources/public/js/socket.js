var webSocket;

function initWS(username) {

    webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/gamesocket/" + username);

    webSocket.onopen = function (msg) {
        console.log("Connected to server.")
    };

    webSocket.onmessage = function (msg) { 
        
    };

    webSocket.onclose = function () { 
        alert("WebSocket connection closed");
    };

}

function sendWSMessage(message) {
    webSocket.send(message);
}



