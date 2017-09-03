$(function () {

});

function initBoard() {
    marketInit("#market-tile");
    opponentInit("#opponent-tile");
    playerBoardInit("#player-board-tile");
    playerHandInit("#player-hand-tile");
}

function opponentInit(selector) {

    var opponent = board.opponent;

    update("opponent", "health", opponent.health);
    update("opponent", "combat", opponent.combat);
    update("opponent", "gold", opponent.gold);    

    var opponentPermanent = opponent.permanent;
    for (var i = 0; i < opponentPermanent.length; i++) {
        $(selector + " .opponent-permanent-board").append(
            divCardPermanent(i, opponentPermanent[i].id, "opponent", 0, 0, 100 / opponentPermanent.length, imgSvg(opponentPermanent[i].code))
        )
    }

    var opponentNonPermanent = opponent.nonpermanent;    
    for (var i = 0; i < opponentNonPermanent.length; i++) {
        $(selector + " .opponent-nonpermanent-board").append(
            divCardPermanent(i, opponentNonPermanent[i].id, "opponent", 0, 0, 100 / opponentNonPermanent.length, imgSvg(opponentNonPermanent[i].code))
        )
    }
}

function playerBoardInit(selector) {
    
    var player = board.player;

    update("player", "health", player.health);
    update("player", "combat", player.combat);
    update("player", "gold", player.gold);  

    updateTurnField(user.username, board.currentPlayer.userName);

    var playerPermanent = player.permanent;

    for (var i = 0; i < playerPermanent.length; i++) {
        $(selector + " .player-permanent-board").append(
            divCardPermanent(i, playerPermanent[i].id, "player", 0, 0, 100 / playerPermanent.length, imgCard(playerPermanent[i].code))
        )
    }

    var playerNonPermanent = player.nonpermanent;
    for (var i = 0; i < playerNonPermanent.length; i++) {
        $(selector + " .player-nonpermanent-board").append(
            divCardPermanent(i, playerNonPermanent[i].id, "player", 0, 0, 100 / playerNonPermanent.length, imgCard(playerNonPermanent[i].code))
        )
    }
}

function playerHandInit(selector) {
    var playerHand = board.player.hand;

    for (var i = 0; i < playerHand.length; i++) {
        $(selector + " .player-hand").append(
            imgCardSvg(i, playerHand[i].id, playerHand[i].code, cards[playerHand[i].code].type, (i == 0))
        )
    }
}

function marketInit(selector) {
    var market = board.market;

    for (var i = 0; i < market.length; i++) {
        $(selector).append(
            marketDiv(i, board.market[i].id, board.market[i].code)
        );
    }

}