$(function () {

});

function initBoard() {
    marketInit("#market-tile");
    opponentInit("#opponent-tile");
    playerBoardInit("#player-board-tile");
    playerHandInit("#player-hand-tile");
}

function opponentInit(selector) {
    var opponentPermanent = board.opponent.permanent;

    for (var i = 0; i < opponentPermanent.length; i++) {
        $(selector + " .opponent-permanent-board").append(
            divCardPermanent(i, opponentPermanent[i].id, "opponent", 0, 0, 100 / opponentPermanent.length, imgSvg(opponentPermanent[i].code))
        )
    }
}

function playerBoardInit(selector) {
    var playerPermanent = board.player.permanent;

    for (var i = 0; i < playerPermanent.length; i++) {
        $(selector + " .player-permanent-board").append(
            divCardPermanent(i, playerPermanent[i].id, "player", 0, 0, 100 / playerPermanent.length, imgCard(playerPermanent[i].code))
        )
    }

    var playerNonPermanent = board.player.nonpermanent;
    for (var i = 0; i < playerNonPermanent.length; i++) {
        $(selector + " .player-permanent-board").append(
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
            divNoId(
                "col-xs-2 col-md-2",
                div(
                    "market-container-" + i,
                    "flip-container",
                    divNoId(
                        "flipper",
                        divNoId(
                            "front",
                            img(
                                "market-slot-img-" + i,
                                "shadow scalable rotated rotated90 market-slot market-slot-" + i,
                                "images/" + board.market[i].code + ".jpg",
                                "Market slot " + i,
                                150,
                                98
                            )
                        ) +
                        divNoId(
                            "back",
                            img(
                                "market-slot-img-" + i,
                                "shadow rotated rotated90 market-slot market-slot-" + i,
                                "images/back-rotate-none.jpg",
                                "Market slot " + i,
                                150,
                                98
                            )
                        )
                    )
                )
            )
        );
    }

}