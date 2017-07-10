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
            $(this).removeClass('scaled').css("transform", "scale(1)");
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