$(function () {
    marketInit("#market-tile");
});

function marketInit(selector) {
    var NUMBER_OF_MARKET_ITEMS = 6;

    var images = ["0001", "0002", "0003", "0011", "0012", "fire-gem"];

    for (var i = 1; i <= NUMBER_OF_MARKET_ITEMS; i++) {
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
                                "images/" + images[i-1] + ".jpg",
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