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
    
    $(".opponent-playable-nonpermanent").click(function() {
        if ($(this).hasClass('scaled')) {
            var transform = "scale(1)";
            if ($(this).hasClass("rotated")) {
                transform += "rotate(90deg)";
            }
            $(this).removeClass('scaled').css("transform", transform);
        } else {
            $(this).addClass("scaled").css("transform", "scale(3.5)");        
        }       
    });

    d3.selectAll(".opponent-playable-nonpermanent.mouseenter-trigger").on("mouseenter", function() {
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
        tapAndActivate(id);
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

function tapAndActivate(id) {
    var cardObject = d3.select("#opponent-card-" + id + " img")
    
    cardObject.transition().duration(300)
        .style("transform", "rotate(90deg)");

    cardObject.classed("rotated", true);
}

