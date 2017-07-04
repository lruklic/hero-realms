var webSocket;

function initWS(username) {

    webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/gamesocket/" + username);

    webSocket.onopen = function (msg) {
        console.log("Connected to server.")
    };

    webSocket.onmessage = function (msg) { 
        console.log(new Date().getTime());
        console.log(msg)
    };

    webSocket.onclose = function () { 
        alert("WebSocket connection closed");
    };

}

function sendWSMessage(message) {
    webSocket.send(message);
}



