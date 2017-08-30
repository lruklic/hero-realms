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

function changeTurnField(playerName, currentPlayer) {
    var text, color;
    if (playerName == currentPlayer) {
        text = "End Turn";
        color = "lightgreen";
    } else {
        text = currentPlayer + "'s turn ...";
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

    var animation = d3.select("#animation");
    var playedCard = d3.select('.hand-card[data-id="' + id + '"]') //d3.select("#hand-" + id);
    
    var playedCardImg = playedCard.select("image").attr("xlink:href");

    d3.select("#animation image")
        .attr("xlink:href", playedCardImg);

    animation
        .classed("shadow", false)
        .style("display", "inherit");

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
            .attr("width", lastElement.attr("width"))
            .attr("height", lastElement.attr("height"));
        
        setTimeout(function() {
            animation.style("display", "none");
        }, 1400);
/*        animation
            .transition().delay(1050).duration(1)
            //.style("display", "none");
            .style("opacity", 0);*/
        setTimeout(function() {
            lastElement.style("opacity", 1);
        }, 1010);
/*        lastElement
            .transition().delay(1020).duration(1)
            .style("opacity", 1);*/

    })

    var startPoint = $(playedCard.node()).offset();

    animation
        .attr("width", playedCard.style("width"))
        .attr("height", playedCard.style("height"))    
        .attr("src", playedCardImg)    
        .style("top", startPoint.top + "px")
        .style("left", startPoint.left + "px") 
        .style("opacity", 1);

    playedCard
        .style("opacity", 0)

}

function acquire(id, oldCard, newCard) {
    d3.select("#market-container-" + id + " .flipper").style("transform", "rotateY(180deg)");

    var offset = $("#market-container-" + id + " .flipper img").offset(); 

    var endPoint = $("#player-discard-pile-image").offset();

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
    
    //console.log(endPoint.top + " " + endPoint.left);
    animation.transition().delay(710).duration(500)
        .style("top", endPoint.top + "px")
        .style("left", endPoint.left + "px")
        .attr("width", 114)
        .attr("height", 75);

/*    animationImage.transition().delay(710).duration(500)
        .attr("width", 114)
        .attr("height", 75);*/

    animation.transition().delay(1220).duration(100)
        .style("opacity", 0)

    setTimeout(function() {
        d3.select("#market-slot-img-" + oldCard.id)
            .attr("id", "market-slot-img-" + newCard.id)
            .attr("src", IMAGES_FOLDER + '/' + newCard.code + IMAGES_FORMAT)
    }, 800)

    setTimeout(function() {
        d3.select("#market-container-" + id + " .flipper").style("transform", "rotateY(0deg)")
        animation.attr("width", 150).attr("height", 98);
    }, 1330);
    
}