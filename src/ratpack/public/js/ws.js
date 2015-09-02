var messageDisplayTemplate = Handlebars.compile($('#message-display-template').html());

function watchTopic(name) {
    if (!window.WebSocket) {
        alert("This won't work in your browser. Try Chrome or a gooder version of Safari.");
    }
    else {
        if (!window.ws || window.ws.readyState != WebSocket.OPEN) {
            window.ws = new WebSocket("ws://" + location.host + "/topics/" + name + "/websocket");

            window.ws.onopen = function(event) {
                console.log("WebSocket opened!");
                $('.progress-bar').addClass('active');
            };
            window.ws.onmessage = function(event) {
                console.log(event);
                var data = $.parseJSON(event.data);
                var messages = $('#messages');
                var el_id = Math.floor(Math.random() * 1000000);
                messages.prepend(messageDisplayTemplate({
                    message_id: el_id,
                    topic: data.topic,
                    partition: data.partition,
                    offset: data.offset,
                    raw: data.raw,
                    b64: data.b64,
                    timestamp: moment(data.tstamp, 'x').format('MMM Do YYYY, H:mm:ss')
                }));

                $("#" + el_id).click(function(ev) {
                    $("#"+el_id+'_b64').toggle();
                });

                // Let's only keep the last 50
                if (messages.children().size() > 50) {
                    messages.children().last().remove();
                }
            };
            window.ws.onclose = function(event) {
                $('.progress-bar').removeClass('active');

                // If our stop button is still visible, we didn't stop manually
                if ($('#stop:visible').size() > 0) {
                    var timer = setTimeout(function() {
                        console.log("Retrying connection...");
                        watchTopic(name);
                        if (window.ws.readyState == WebSocket.OPEN) {
                            clearTimeout(timer);
                        }
                    }, 1000);
                }
            };
        }
    }
}
