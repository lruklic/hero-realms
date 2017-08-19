var webSocket;

var user = {};

function initWS(username) {

    webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/gamesocket/" + username);
    user.username = username;

    webSocket.onopen = function (msg) {
        console.log("Connected to server.")
    };

    webSocket.onmessage = function (msg) {
        console.log("SERVER :" + msg.data);
        if (jQuery.isEmptyObject(board)) {
            board = JSON.parse(msg.data);
            initBoard();
        } else {
            mapAction(msg.data);
            checkForChanges(JSON.parse(msg.data));
        }
    };

    webSocket.onclose = function () { 
        alert("WebSocket connection closed");
    };

}

function sendWSMessage(message) {
    webSocket.send(user.username + " " + message);
}



