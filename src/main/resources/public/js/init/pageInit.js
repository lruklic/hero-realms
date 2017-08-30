$(function() {
    var images = ['DARREW', 'FIRGEM', 'INFLUE', 'THEROT', 'CULPRI', 'DEACUL', 'RAYEND'];
    preload(images);

});

function preload(arrayOfImages) {
    $(arrayOfImages).each(function () {
        $('<img />').attr('src', IMAGES_FOLDER + '/' + this + IMAGES_FORMAT).appendTo('body').css('display','none');
    });
}