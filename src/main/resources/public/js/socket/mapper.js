function mapAction(command) {

    var action = command.split(" ")[0];

    switch(action) {
        case "acquire":
            var acquiredCardId = command.split(" ")[1];
            acquire(acquiredCardId);
            break;
        default:
            break;
    }
}