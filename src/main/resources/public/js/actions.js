/**
 * Updates value on token.
 * 
 * @target player, opponent
 * @category gold, combat, health
 * @value final value in token after update
 */

function update(target, category, value) {
    d3.select("svg." + target  + "-" + category + "-token text").transition().duration(1000).tween("text", function() {
        var that = d3.select(this);
        var i = d3.interpolateNumber(that.text(), value);
        return function(t) { that.text(Math.round(i(t))); };
    });
}

function updateTurnField(playerName, currentPlayer) {
    var container = d3.select("svg.player-end-turn");
    var text, color;
    if (playerName == currentPlayer) {
        text = "End Turn";
        color = "lightgreen";
    } else {
        text = currentPlayer + "'s turn ...";
        color = "indianred";
    }

    var g = container
        .append("g")
        .attr("class","button");
    g.append("rect")
        .attr("x", "1%").attr("y", "1")
        .attr("height", "50").attr("width", "98%")
        .attr("fill", color).attr("stroke", "#080808");
    g.append("text")
        .attr("x", "50%").attr("y", "25")
        .attr("alignment-baseline", "middle")
        .attr("text-anchor", "middle")
        .attr("font-weight", "bold")
        .text(text);
}
/**
 * Change End turn button text and color.
 * 
 * @param playerName name of the player that controls the browser
 * @param currentPlayerName name of the player that has the turn
 * 
 */
function changeTurnField(playerName, currentPlayerName) {
    var text, color;
    if (playerName == currentPlayerName) {
        text = "End Turn";
        color = "lightgreen";
    } else {
        text = currentPlayerName + "'s turn ...";
        color = "indianred";
    }

    d3.select(".player-end-turn rect").transition().duration(2500).style("fill", color);

    d3.select(".player-end-turn text")
        .transition()
        .duration(2500)
        .style("opacity", 0)
        .remove();
    d3.select(".player-end-turn g")
        .append("text")
        .attr("x", "50%").attr("y", "25")
        .attr("alignment-baseline", "middle")
        .attr("text-anchor", "middle")
        .attr("font-weight", "bold")
        .style("opacity", 0)
        .text(text)
        .transition().duration(2500)
        .style("opacity", 1);
}

function updateDeckCardsLeft(target, numberOfCards) {
    $("#" + target + "-deck-text strong").text("Deck: " + numberOfCards + " left");
}

function tapAndActivate(target, id) {
    var cardObject = d3.select("#" + target + "-card-" + id + " img")
    
    cardObject.classed("mouseenter-trigger", false);

    d3.select(cardObject.node().parentNode)
        .select(".ability-picker")
        .style("display", "none");

    cardObject.transition().duration(300)
        .style("transform", "rotate(90deg)");

    cardObject.classed("rotated", true);
}

function playCard(id, type) {
    var boardSelector;
    if (type.toUpperCase() == "CHAMPION") {
        boardSelector = ".player-permanent-board";
    } else {
        boardSelector = ".player-nonpermanent-board";
    }
    // check if permanent or nonpermanent

    var animation = d3.select("#animation-0");
    var playedCard = d3.select('.hand-card[data-id="' + id + '"]'); //d3.select("#hand-" + id);
    
    var playedCardImg = playedCard.select(".vertical-front image").attr("xlink:href");

    animation.select("image")
        .attr("xlink:href", playedCardImg);

    animation
        .classed("shadow", false)
        .style("display", "inherit")
        .style("transform", "rotate(0deg)");

    var selection = d3.selectAll(boardSelector + " .card-container");
    var oldSelectionSize = selection.size();
    var percentageSize = 100 / (oldSelectionSize + 1);

    $(boardSelector)
        .append('<div class="card-container player-playable-' + (oldSelectionSize + 1) + '" style="width: 0%">' +
            '<svg height="126px" width="90px" class="scalable transparent0"><image xlink:href="' + playedCardImg + '"/></svg>' + '</div>')

    d3.selectAll(boardSelector + " .card-container").transition().duration(500).style("width", percentageSize + "%");

    var lastElement = d3.select(boardSelector + " .player-playable-" + (oldSelectionSize + 1) + " svg");
    lastElement.transition().duration(500).on("end", function() { 
        var endPoint = $(boardSelector + " .player-playable-" + (oldSelectionSize + 1) + " svg").offset();
        
        animation
            .transition().delay(510).duration(500)
            .style("top", endPoint.top + "px")
            .style("left", endPoint.left + "px")
            .style("width", lastElement.attr("width"))
            .style("height", lastElement.attr("height"));
        
        setTimeout(function() {
            animation
                .style("width", 0)
                .style("height", 0);
        }, 1400);

        setTimeout(function() {
            lastElement.style("opacity", 1);
            $(playedCard.node()).parents(".scale-container").remove();
        }, 1010);

    })

    var startPoint = $(playedCard.node()).offset();

    animation
        .style("width", playedCard.style("width"))
        .style("height", playedCard.style("height"))    
        .attr("src", playedCardImg)    
        .style("top", startPoint.top + "px")
        .style("left", startPoint.left + "px") 
        .style("opacity", 1);

    playedCard
        .style("opacity", 0)

}

function drawHand(hand) {

    $(".player-hand").empty();

    for (var i = 0; i < hand.length; i++) {
        drawCard(i, hand[i]);
    }
}

function drawCard(i, cardDrawn) {

    var offset = $("#player-deck-image").offset();

    var animation = d3.select("#animation-" + i);
    var animationImage = animation.select("image");

    var d3PlayerHand = d3.select(".player-hand");
    d3PlayerHand
        .transition().delay(300).duration(300)
        .style("padding-left", (parseInt(d3PlayerHand.style("padding-left")) + 20) + "px"); 

    animationImage
        .attr("xlink:href", "images/back-rotate-right.jpg");

    animation
        .style("transform-origin", "40% 60%")
        .style("top", offset.top + "px")
        .style("left", offset.left + "px")
        .attr("width", 114)
        .attr("height", 75)
        .style("transform", "rotate(0deg)")
        .transition().delay(700).duration(1)
        .style("opacity", 1);

    var playerHand = $(".player-hand");    

    var handCardsCount = playerHand.find(".vertical-front .hand-card").length;

    var handCard = $($.parseHTML(handDiv(handCardsCount, cardDrawn, cards[cardDrawn.code], 40 * handCardsCount)));
    handCard.css("opacity", 0);

    playerHand.append(handCard);
    var newCardOffset = handCard.offset();

    var d3HandCard = d3.select("#hand-card-" + handCardsCount);
    d3HandCard.select(".flipper").style("transform", "rotateY(180deg)");
    //var newCardOffset = d3HandCard.offset();

    animation
        .transition().delay(710).duration(700)
        .style("top", (newCardOffset.top + 25) + "px")
        .style("left", newCardOffset.left + 15 + "px")
        .style("transform", "rotate(-90deg)")
        .attr("height", 110)
        .attr("width", 145)

    animation
        .transition().delay(1410).duration(1)
        .style("opacity", 0)
        
    d3HandCard
        .transition().delay(1410).duration(1)
        .style("opacity", 1);

    var rotateInterpolator = d3.interpolateString("rotateY(180deg)", "rotateY(0deg)");

    d3HandCard.select(".flipper")
        .transition().delay(1420)
        .styleTween('transform', function (d) {
            return rotateInterpolator;
        });
    
    animation
        .transition().delay(1430).duration(1)
        .attr("width", 0)
        .attr("height", 0)
}

function discardHand(hand) {
    for (var i = 0; i < hand.length; i++) {

        var handCard = d3.select("#hand-card-" + i);

        if (!handCard.empty() && handCard.style("opacity") > 0) {
            handCard.select(".flipper").style("transform", "rotateY(180deg)");
            
            var offset = $("#hand-card-" + i + " .flipper svg").offset();
            var endPoint = $("#player-discard-pile-image").offset();
    
            var animation = d3.select("#animation-" + i);
            var animationImage = animation.select("image");
            
            animationImage
                .attr("xlink:href", "images/printplay/card-back-vertical.png");
        
            animation
                .attr("width", 100)
                .attr("height", 150)
                .style("top", offset.top + "px")
                .style("left", offset.left + "px")
                .transition().delay(700).duration(1)
                .style("opacity", 1)
                .style("display", "inherit")
        
            animation.transition().delay(710).duration(1000)
                .style("transform", "rotate(90deg)")
                .style("top", endPoint.top + "px")
                .style("left", endPoint.left + "px")
                .attr("width", 75)
                .attr("height", 114);
    
            animation.transition().delay(1710).duration(100)
                // pomaknuti u lijevo, povecaj height
                .style("opacity", 0)
    
            handCard.transition().delay(700).duration(1)
                .style("opacity", 0);
        }
    }
}

function discardNonPermanent(nonPermanent, delay) {
    var delay = (delay ? delay : 0);

    var nonPermanent = d3.selectAll(".player-nonpermanent-board .card-container");

    nonPermanent.transition().delay(delay).duration(1000)
        .style("opacity", 0);
    nonPermanent.transition().delay(delay + 1000)
        .remove();
}

function resetTokens(target, player) {
    update(target, "combat", player.combat);
    update(target, "gold", player.gold);
}

function acquire(id, oldCard, newCard, isPlayerTurn) {
    d3.select("#market-container-" + id + " .flipper").style("transform", "rotateY(180deg)");

    var offset = $("#market-container-" + id + " .flipper img").offset(); 


    var endPoint; 
    if (isPlayerTurn) {
        endPoint = $("#player-discard-pile-image").offset();
    } else {
        endPoint = $("#opponent-discard-pile-image").offset();
    }

    var animation = d3.select("#animation");
    var animationImage = d3.select("#animation image");

    animation
        .style("top", offset.top + "px")
        .style("left", offset.left + "px")
        .attr("width", 150)
        .attr("height", 98)
        .transition().delay(700).duration(1)
        .style("opacity", 1)
        .style("display", "inherit")

    animationImage
        .attr("xlink:href", "images/back-rotate-right.jpg")
    
    animation.transition().delay(710).duration(500)
        .style("top", endPoint.top + "px")
        .style("left", endPoint.left + "px")
        .attr("width", 114)
        .attr("height", 75);

/*    animationImage.transition().delay(710).duration(500)
        .attr("width", 114)
        .attr("height", 75);*/

    animation.transition().delay(1220).duration(100)
        .style("opacity", 0);

    setTimeout(function() {
        d3.select("#market-slot-img-" + oldCard.id)
            .attr("id", "market-slot-img-" + newCard.id)
            .attr("src", IMAGES_FOLDER + '/' + newCard.code + IMAGES_FORMAT)
    }, 800)

    setTimeout(function() {
        d3.select("#market-container-" + id + " .flipper").style("transform", "rotateY(0deg)")
        animation
            .attr("width", 150)
            .attr("height", 98)
            .style("top", 0)
            .style("left", 0);
    }, 1330);
    
}