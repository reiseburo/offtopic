function watchTopic(name) {
    if (!window.WebSocket) {
        alert("This won't work in your browser. Try Chrome or a gooder version of Safari.");
    }
    else {
        if (!window.ws || window.ws.readyState != WebSocket.OPEN) {
            window.ws = new WebSocket("ws://" + location.host + "/topics/" + name + "/websocket");

            window.ws.onopen = function(event) {
                console.log("WebSocket opened!");
            };
            window.ws.onmessage = function(event) {
                console.log(event);
                var messages = $('#messages')
                messages.prepend("<div class='list-group-item'>" + event.data + "</div>");

                // Let's only keep the last 25
                if (messages.children().size() > 25) {
                    messages.children().last().remove();
                }
            };
            window.ws.onclose = function(event) {
                var timer = setTimeout(function() {
                    console.log("Retrying connection...");
                    watchTopic(name);
                    if (window.ws.readyState == WebSocket.OPEN) {
                        clearTimeout(timer);
                    }
                }, 1000);
            };
        }
    }
}
