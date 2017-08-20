var cardAbilityHover = {"id" : null, "active" : false};

var board = {
    "opponent" : {
        "health" : 55,
        "damage" : 0,
        "gold" : 0,
        "deck" : 23,
        "discard" : [

        ],
        "permanent" : [
/*            {"id" : "001", "code" : "CH-CAPKOS"},
            {"id" : "002", "code" : "CH-CAPKOS"}, 
            {"id" : "003", "code" : "CH-CAPKOS"}*/            
        ],
        "nonpermanent" : [
        ]
    },
    "market" : [
        {"id" : "004", "code" : "CH-RAYEND"},
        {"id" : "005", "code" : "CH-CULPRI"},
        {"id" : "006", "code" : "CH-DEACUL"},
        {"id" : "007", "code" : "AC-THEROT"},
        {"id" : "008", "code" : "AC-INFLUE"},
        {"id" : "009", "code" : "AC-FIRGEM"}
    ],
    "player" : {
        "health" : 55,
        "damage" : 0,
        "gold" : 0,
        "deck" : 20,
        "discard" : [

        ],
        "permanent" : [
            /*{"id" : "010", "code" : "CH-CAPKOS"}*/
        ],
        "nonpermanent" : [
        ],
        "hand" : [
            {"id" : "011", "code" : "DAGGER"},
            {"id" : "016", "code" : "DAGGER"},            
            {"id" : "012", "code" : "GOLD00"},
            {"id" : "013", "code" : "GOLD00"},
            {"id" : "014", "code" : "GOLD00"},
            {"id" : "015", "code" : "GOLD00"},            
            /*{"id" : "014", "code" : "CH-VARNEC"} */       
        ]
    }
}
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

    $("#opponent-deck-image")
        .mouseenter(function() {
            $(this).css("opacity", 0.3);
            
            var offset = $(this).offset();

            $("#opponent-deck-text")
                .css({
                    "top" : offset.top,
                    "left" : offset.left
                })
                .width($(this).width())
                .height($(this).height())
                .removeClass("hidden");
        })

    $("#opponent-deck-text").mouseleave(function() {
        $("#opponent-deck-image").css("opacity", 1);
        $("#opponent-deck-text").addClass("hidden");
    });

    $("#market-tile").on("click", ".market-slot", function() {
        var idArray = $(this).attr("id").split("-");
        var id = idArray[idArray.length - 1];
        sendWSMessage("BUY " + id);
    });

    $('#player-hand-tile').on("click", ".hand-card", function() {
        var idArray = $(this).attr("id").split("-");
        var id = idArray[idArray.length - 1];
        sendWSMessage("PLAY " + id);
    });

    $("#player-hand-tile")
        .on("mouseenter", ".hand-card", function() {       
            $(this).css("transform", "translate(0px, -20px)")
        })
        .on("mouseleave", ".hand-card", function() {
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

/*    d3.selectAll(".hand-card").on("click", function() {
        var handCard = d3.select(this);
        var id = handCard.attr("id").split("-")[1];
        var type = handCard.attr("data-type");
        playCard(id, type);
    });*/

}





