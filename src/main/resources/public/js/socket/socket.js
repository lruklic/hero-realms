var webSocket;

var user = {};

function initWS(username) {

    webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/gamesocket/" + username);
    user.username = username;

    webSocket.onopen = function (msg) {
        console.log("Connected to server.")
    };

    webSocket.onmessage = function (msg) {
        mapMessage(msg);
    };

    webSocket.onclose = function () { 
        alert("WebSocket connection closed");
    };

}

function sendWSMessage(message) {
    webSocket.send(createSocketMessage(board.match.uuid, user.username, message));    
    //webSocket.send(board.match.uuid + " " + user.username + " " + message);
}

function createSocketMessage(matchUUID, username, message) {
    var socketMessage = {
        "match" : matchUUID,
        "user" : username,
        "message" : {
            "action" : message.action,
            "cardId" : message.cardId
        }
    };

    return JSON.stringify(socketMessage);
}



