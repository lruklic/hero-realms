$(function() {
    var images = ['DARREW.jpg', 'FIRGEM.jpg', 'INFLUE.jpg', 'THEROT.jpg', 'CAPKOS.jpg', 'CULPRI.jpg', 'DEACUL.jpg', 'RAYEND.jpg'];
    preload(images);

});

function preload(arrayOfImages) {
    $(arrayOfImages).each(function () {
        $('<img />').attr('src', IMAGES_FOLDER + '/' + this).appendTo('body').css('display','none');
    });
}