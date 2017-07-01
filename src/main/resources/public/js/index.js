$(function() {
    $("#wsconnect").click(() => {
        var username = $("#username").val();
        initWS(username);
    });

    $("#sendMessage").click(() => {
        var message = $("#message").val();
        sendWSMessage(message);
    });
});