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
            $(this).css("opacity", 0.5);
        })
        .mouseleave(function() {
            $(this).css("opacity", 1);
            $("#opponent-deck-image-text").css("opacity", 1);
        });
    
    $(".scalable").click(function() {
        if ($(this).hasClass('scaled')) {
            var transform = "scale(1)";
            if ($(this).hasClass("rotated")) {
                transform += "rotate(90deg)";
            }
            d3.select(this).classed('scaled', false)
                .style("transform", transform)
                .transition().duration(200)
                .style("z-index", 99);
        } else {
            var maxZIndex = 0;
            d3.selectAll(".scalable").each(function() {
                var zIndex = Number(d3.select(this).style("z-index"));
                if (zIndex > maxZIndex) maxZIndex = zIndex;
            });
            
            d3.select(this).classed("scaled", true)
                .style("transform", "scale(3.5)")
                .style("z-index", Number(maxZIndex) + 1);        
        }       
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

function acquire() {
    d3.select(".flipper").style("transform", "rotateY(180deg)");
    
    //var clone = $(".flipper").clone(); 
    var offset = $(".flipper img").offset(); 
    
/*    clone.attr("id", "animation").css({
        "position" : "absolute",
        "left"     : offset.left + 150,
        "top"      : offset.top,
        "opacity" : 0
    }).appendTo($(".container"));*/

    var endPoint = $("#player-discard-pile-image").offset();

    var animation = d3.select("#animation");

    animation
        .style("top", offset.top + "px")
        .style("left", offset.left + "px")
        .transition().delay(700).duration(1)
        .style("opacity", 1);
    
    //console.log(endPoint.top + " " + endPoint.left);
    animation.transition().delay(710).duration(500)
        .style("top", endPoint.top + "px")
        .style("left", endPoint.left + "px")
        .attr("width", 114)
        .attr("height", 75);

    animation.transition().delay(710 + 510).duration(100)
        .style("opacity", 0)

    
}