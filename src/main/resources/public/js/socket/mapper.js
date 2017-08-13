function mapAction(command) {

    var action = command.split(" ")[0];

    switch(action) {
        case "acquire":
            var acquiredCardId = command.split(" ")[1];
            acquire(acquiredCardId);
            break;
        case "play":
            var playedCardId = command.split(" ")[1];
            var playerCardType = cards[acquiredCardId];
            playCard(playedCardId, playerCardType);
            break;
        default:
            break;
    }
}
/**
 * Method that checks for changes between old board state and new board state and propagate in on FE.
 */
function checkForChanges(newBoardState) {

    for (var i = 0; i < board.market.length; i++) {
        var newMarket = newBoardState.market;
        if (board.market[i].code != newMarket[i].code) {
            acquire(i, newMarket[i].code);
        }
    }

}