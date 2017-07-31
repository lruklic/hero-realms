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

function divCardPermanent(index, id, player, hp, guard, widthPercentage, content) {
    return '<div id="' + player + '-card-' + index + '" data-id="' + id + '" data-hp="' + hp + '"' +
        ' data-guard="' + guard + '" class="card-container" style="width:' + widthPercentage +'%">' + content + '</div>'; 
}

function imgSvg(src) {
    return '<svg height="126px" width="90px" class="scalable mouseenter-trigger">'
        + '<image xlink:href="images/' + src + '.jpg"/></svg>'
}

function imgCardSvg(index, id, src, type, isFirst) {
    var rest = "rest";
    if (isFirst) rest = "";

    return '<svg id="hand-' + index + '" data-id="' + id + '" data-type="' + type + '" class="hand-card scalable ' + rest + '">'
        + '<image xlink:href="images/' + src + '.jpg"/>' + '</svg>'
}

function imgCard(src) {
    return '<img class="shadow scalable mouseenter-trigger" src="images/' + src + '.jpg" alt="' + src + '" height="114" width="75"></img>';
}