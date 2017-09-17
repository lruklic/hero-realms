var IMAGES_FOLDER = "images/printplay";
var IMAGES_FORMAT = ".png";
var MARKET_CARD_HEIGHT = 150;
var MARKET_CARD_WIDTH = 98;

function div(id, clazz, content, arg1) {
    if (!arg1) arg1 = "";
    if (!id) { 
        id = "";
    } else {
        id = 'id="' + id + '"';
    }
    return '<div ' + id + " " + arg1 + ' class="' + clazz + '">' + content + '</div>';
}

function divNoId(clazz, content) {
    return '<div class="' + clazz + '">' + content + '</div>';    
}

function img(id, clazz, src, alt, height, width) {
    return '<img id="' + id + '" class="' + clazz + '" src="' + src + '" alt="' + alt + '" height="' + height + '" width="' + width + '"></img>'
}

function button(clazz, content) {
    return '<button type="button" class="' + clazz + '">' + content + '</button>';
}

function itag(clazz) {
    return '<i class="' + clazz + '"></i>';
}

/* function divCardPermanent(index, id, player, hp, guard, widthPercentage, content) {
    return '<div id="' + player + '-card-' + index + '" data-id="' + id + '" data-hp="' + hp + '"' +
        ' data-guard="' + guard + '" class="card-container" style="width:' + widthPercentage +'%">' + content + '</div>'; 
} */

function divCardPermanent(i, widthPercentage, playedCardImg) {
    return '<div class="card-container player-playable-' + i + '" style="width:' + widthPercentage + '%">' + playedCardImg + '</div>'
}

function imgSvg(src) {
    return '<svg height="126px" width="90px" class="scalable mouseenter-trigger">'
        + '<image xlink:href="' + IMAGES_FOLDER + '/' + src + IMAGES_FORMAT + '"/></svg>'
}

/**
 * Create card image SVG for hand card.
 */
function imgCardSvg(index, id, src, type) {
    return '<svg id="hand-' + index + '" data-id="' + id + '" data-type="' + type + '" class="hand-card scalable">'
        + '<image xlink:href="' + IMAGES_FOLDER + '/' + src + IMAGES_FORMAT + '"/>' + '</svg>'
}

function imgMarketFront(i, cardId, cardCode) {
    return img(
        "market-slot-img-" + cardId,
        "shadow scalable rotated rotated90 market-slot market-slot-" + i,
        IMAGES_FOLDER + '/' + cardCode + IMAGES_FORMAT,
        "Market slot " + i,
        MARKET_CARD_HEIGHT,
        MARKET_CARD_WIDTH
    )
}

function imgMarketBack(i, cardId) {
    return img(
        "market-slot-img-back-" + cardId,
        "shadow rotated rotated90 market-slot-" + i,
        "images/back-rotate-none.jpg", 
        "Market slot " + i,
        MARKET_CARD_HEIGHT,
        MARKET_CARD_WIDTH
    )
}

function marketDiv(index, cardId, cardFrontCode) {
    return '<div class="col-xs-2 col-md-2">' +
                 '<div id="market-container-' + index + '" class="scale-container flip-container">' +
                    '<div class="flipper">' +
                        '<div class="front">' + imgMarketFront(index, cardId, cardFrontCode) + '</div>' +
                        '<div class="back">' + imgMarketBack(index, cardId) + '</div>' +
            '</div></div></div>';
}

/**
 * Create container for one hand card.
 * 
 * @param i index of card
 * @param handCard single hand card element from board
 * @param card selected card prototype stored in memory retrieved by unique ID
 * 
 */
// i, playerHand[i], cards[playerHand[i].code]
function handDiv(i, handCard, card, margin) {
    return '<div id="hand-card-' + i + '" class="scale-container vertical-flip-container" style="margin-left:-' + margin + 'px"><div class="flipper"><div class="vertical-front">' 
        + imgCardSvg(i, handCard.id, handCard.code, card.type)  + '</div>'
        + '<div class="vertical-back">' + imgCardSvg(i, "back-" + handCard.id, "card-back-vertical", "NONE") + '</div></div></div>'
}

function animationSvg(i) {
    return '<svg id="animation-' + i + '" width="0px" class="animation absolute transparent0">' +
        '<image xlink:href="" height="100%" width="100%"/></svg>';
}