
$(document).ready(function () {
    $("#cke_38").remove();
    let click = $('1').click(function () {

        var d = $('#form_push').serializeArray();
        var value = CKEDITOR.instances['id_note_text'].getData();

        $.ajax({
            method:"POST",

            url: '/cocktail',
            data:{


            }
            .done(function( msg ) {
                    alert( "Data Saved: " + msg );
            })
        });
    });
});