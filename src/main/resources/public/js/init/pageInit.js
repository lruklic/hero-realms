$(function() {
    var images = ['0001.jpg', '0002.jpg', '0003.jpg', '0011.jpg', '0012.jpg', '0013.jpg', 'fire-gem.jpg', 'koska.jpg'];
    preload(images);

});

function preload(arrayOfImages) {
    $(arrayOfImages).each(function () {
        $('<img />').attr('src',"images/" + this).appendTo('body').css('display','none');
    });
}