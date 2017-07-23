var cardAbilityHover = {"id" : null, "active" : false};

$(function() {

    gui();

    $("#wsconnect").click(() => {
        var username = $("#username").val();
        initWS(username);
    });

    $("#sendMessage").click(() => {
        var message = $("#message").val();
        sendWSMessage(message);
    });
});

function gui() {

    $("#opponent-deck-image")
        .mouseenter(function() {
            $(this).css("opacity", 0.3);
            
            var offset = $(this).offset();

            $("#deck-text")
                .css({
                    "top" : offset.top,
                    "left" : offset.left
                })
                .width($(this).width())
                .height($(this).height())
                .removeClass("hidden");
        })

    $("#deck-text").mouseleave(function() {
        $("#opponent-deck-image").css("opacity", 1);
        $("#deck-text").addClass("hidden");
    });

    $("#market-tile").on("click", ".market-slot", function() {
        var idArray = $(this).attr("id").split("-");
        var id = idArray[idArray.length - 1];
        acquire(id);
    });

    $(".hand-card")
        .mouseenter(function() {
            $(this).css("transform", "translate(0px, -20px)")
        })
        .mouseleave(function() {
            $(this).css("transform", "translate(0px, 0px)")
        })

    $(".container").on("contextmenu", ".scalable", function() {
        if ($(this).hasClass('scaled')) {
            var transform = "scale(1)";
            if ($(this).hasClass("rotated")) {
                transform += "rotate(90deg)";
            }
            $(this).removeClass('scaled')
                .css("transform", transform);
            d3.select($(this).parents(".flip-container").get(0))
                .transition().duration(200)
                .style("z-index", 99);
        } else {
            var maxZIndex = 0;

            $(".scalable").parents(".flip-container").each(function() {
                var zIndex = Number(d3.select(this).style("z-index"));
                if (zIndex > maxZIndex) maxZIndex = zIndex;
            });

/*            d3.selectAll(".scalable").each(function() {
                var zIndex = Number(d3.select(this).style("z-index"));
                if (zIndex > maxZIndex) maxZIndex = zIndex;
            });*/
            
            $(this).addClass("scaled")
                .css("transform", "scale(3.5)")
            $(this).parents(".flip-container")
                .css("z-index", Number(maxZIndex) + 2);        
        }       
        return false;
    });

    d3.selectAll(".opponent-playable-nonpermanent.mouseenter-trigger, .player-playable-nonpermanent.mouseenter-trigger").on("mouseenter", function() {
        
        if (!d3.select(this).classed("mouseenter-trigger")) {
            return;
        }

        cardAbilityHover.id = d3.select(this).attr('id');
        cardAbilityHover.active = true;
        d3.select(this.parentNode).select("div")
            .transition().duration(500)
            .style("opacity", 1)
            //.classed("hidden", false);
    });

    d3.selectAll(".card-container").on("mouseleave", function() {
        d3.select(this).select("div")
            .transition().duration(500)
            .style("opacity", 0)
            //.classed("hidden", true);
    });

    d3.selectAll(".ability-picker").on("click", function() {
        var id = d3.select(this.parentNode).attr("id").split("-")[2];
        tapAndActivate("opponent", id);
        update("opponent", "combat", 5);
    });

/*    $(".opponent-playable-nonpermanent").mouseenter(function() {
        $(this).siblings("div").removeClass("hidden")
    });*/

/*    $(".opponent-playable-nonpermanent")
        .mouseenter(function() {
            $(this).css("transform", "scale(2)");
        })
        .mouseleave(function() {
            $(this).css("transform", "scale(1)");
        });*/
}
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

function playCard(id) {
    // check if permanent or nonpermanent

    var animation = d3.select("#animation");
    var playedCard = d3.select("#hand-" + id);
    var playedCardImg = playedCard.select("image").attr("xlink:href");

    d3.select("#animation image")
        .attr("xlink:href", playedCardImg);

    animation
        .classed("shadow", false)
        .style("display", "inherit");

    var selection = d3.selectAll(".player-playable-field");
    var oldSelectionSize = selection.size();
    var percentageSize = 100 / (oldSelectionSize + 1);

    $(".player-permanent-board")
        .append('<div class="player-playable-field player-playable-' + (oldSelectionSize + 1) + '" style="width: 0%">' +
            '<svg height="126px" width="90px" class="scalable transparent0"><image xlink:href="' + playedCardImg + '"/></svg>' + '</div>')

    d3.selectAll(".player-playable-field").transition().duration(500).style("width", percentageSize + "%");

    var lastElement = d3.select(".player-playable-" + (oldSelectionSize + 1) + " svg");
    lastElement.transition().duration(500).on("end", function() { 
        var endPoint = $(".player-playable-" + (oldSelectionSize + 1) + " svg").offset();

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

/*    $("#player-card-4")
        .append('<img class="shadow player-playable-nonpermanent scalable mouseenter-trigger" src="images/koska.jpg" alt="Deck" height="114" width="75">')
        .addClass("column")*/
}

function acquire(id) {
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
        d3.select("#market-slot-img-" + id).attr("src", "images/0013.jpg")
    }, 800)

    setTimeout(function() {
        d3.select("#market-container-" + id + " .flipper").style("transform", "rotateY(0deg)")
        animation.attr("width", 150).attr("height", 98);
    }, 1330);
    
}