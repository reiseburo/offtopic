/* Bind all our multipass buttons */
$('.add-multipass').click(function(e) {

    $('.multipass-panel').show();

    $('.multipass-body').prepend([
            '<span class="multipass-label label label-warning">',
            $(e.target).attr('data-topic'),
            '</span>'
    ].join("\n"));
});


$('.go-multipass').click(function(e) {
    var topics = [];

    $('.multipass-label').each(function(idx, el) {
        /* Add the topic stripping all the whitespace from the text node jQuery
         * gives us back
         */
        topics.push($(el).text().replace(/\s/g, ''));
    });

    document.location.href = "/topics/" + topics.join('+') + "/watch";
});
