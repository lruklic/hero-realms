$(function() {
    var images = ['AC-DARREW.jpg', 'AC-FIRGEM.jpg', 'AC-INFLUE.jpg', 'AC-THEROT.jpg', 'CH-CAPKOS.jpg', 'CH-CULPRI.jpg', 'CH-DEACUL.jpg', 'CH-RAYEND.jpg'];
    preload(images);

});

function preload(arrayOfImages) {
    $(arrayOfImages).each(function () {
        $('<img />').attr('src',"images/" + this).appendTo('body').css('display','none');
    });
}