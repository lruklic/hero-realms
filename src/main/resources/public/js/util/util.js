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