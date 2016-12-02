
/* local scripts */

$(function() {
    // Autocomplete
    // Ref: https://github.com/JustGoscha/allmighty-autocomplete

    // Datepicker
    // Ref: https://github.com/eternicode/bootstrap-datepicker
    $('.input-group.date').datepicker({
        format: "MM d",
        orientation: 'top left',
        autoclose: true,
        todayHighlight: true
    });



    // showing next line in the note content
    // http://stackoverflow.com/questions/863779/javascript-how-to-add-line-breaks-to-an-html-textarea
    // text.replace(/\r?\n/g, '<br />')

});