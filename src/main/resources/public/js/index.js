var cardAbilityHover = {"id" : null, "active" : false};

var board = {};
var cards = {};

$(function() {

    $.ajax({
        url : "/cardsJson",
        type : "GET",
        //contentType: 'application/json',
        success : function(data) {
            var cardsData = JSON.parse(data).cards;
            for (var i = 0; i < cardsData.length; i++) {
                cards[cardsData[i].code] = cardsData[i];
            }
            console.log(cards);

            gui();
        }
    });

    $("#wsconnect").click(() => {
        var username = $("#username").val();
        initWS(username);
    });

});

function gui() {

    var decksMap = {"deck" : "Deck : ", "discardPile" : "Discard pile : "};

    $("#opponent-deck-image, #opponent-discard-pile-image")
        .mouseenter(function() {
            $(this).css("opacity", 0.3);
            
            var dataSelector = $(this).attr("data-selector").split("-");

            var target = dataSelector[0];
            var deckType = dataSelector[1];

            $("#deck-text strong").text(decksMap[deckType] + getDeckCount(target, deckType));

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

        // riješiti s hover https://stackoverflow.com/questions/20528124/mouseleave-doesnt-trigger-when-mouseenter-is-not-completed-yet

    $("#opponent-tile").on("click", ".opponent-health-token", function() {
        sendWSMessage({"action" : "DAMAGE"});
    });

    $("#deck-text").mouseleave(function() {
        $("#opponent-deck-image, #opponent-discard-pile-image").css("opacity", 1);
        $("#deck-text").addClass("hidden");
    });

    $("#market-tile").on("click", ".market-slot", function() {
        var idArray = $(this).attr("id").split("-");
        var id = idArray[idArray.length - 1];
        sendWSMessage({"action" : "BUY", "cardId" : id});
    });

    $('#player-hand-tile').on("click", ".player-end-turn", function() {
        sendWSMessage({"action" : "END"});
    });

    $('#player-hand-tile').on("click", ".hand-card", function() {
        var id = $(this).attr("data-id");
        sendWSMessage({"action" : "PLAY", "cardId" : id});
    });

    $("#player-hand-tile")
        .on("mouseenter", ".hand-card", function() {   
            if (!$(this).hasClass('scaled')) {
                $(this).css("transform", "translate(0px, -20px)")                
            } 
        })
        .on("mouseleave", ".hand-card", function() {
            if (!$(this).hasClass('scaled')) {                
                $(this).css("transform", "translate(0px, 0px)")
            }
        })

    $(".container").on("contextmenu", ".scalable", function() {
        if ($(this).hasClass('scaled')) {
            var transform = "scale(1)";
            if ($(this).hasClass("rotated")) {
                transform += "rotate(90deg)";
            }
            $(this).removeClass('scaled')
                .css("transform", transform);
            d3.select($(this).parents(".scale-container").get(0))
                .transition().duration(200)
                .style("z-index", 99);
        } else {
            var maxZIndex = 0;

            $(".scalable").parents(".scale-container").each(function() {
                var zIndex = Number(d3.select(this).style("z-index"));
                if (zIndex > maxZIndex) maxZIndex = zIndex;
            });
            
            $(this).addClass("scaled")
                .css("transform", "scale(3.5)")
            $(this).parents(".scale-container")
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
    });

    d3.selectAll(".ability-picker").on("click", function() {
        var id = d3.select(this.parentNode).attr("id").split("-")[2];
        tapAndActivate("opponent", id);
        update("opponent", "combat", 5);
    });

}





