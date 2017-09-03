function getDeckCount(target, deckType) {
    if (deckType == "deck") {
        return board[target]["deck"];
    } else if (deckType == "discardPile") {
        var discardPile = board[target].discardPile;
        return discardPile ? discardPile.length : 0;
    }
}