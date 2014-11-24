import ratpack.handlebars.HandlebarsModule
import ratpack.jackson.JacksonModule

import static ratpack.handlebars.Template.handlebarsTemplate
import static ratpack.jackson.Jackson.json
import static ratpack.groovy.Groovy.*
import static ratpack.websocket.WebSockets.websocket

import groovy.json.*

import offtopic.curator.CuratorPool

ratpack {
    bindings {
        add new HandlebarsModule()
        add new JacksonModule()
    }
    handlers {
        get {
            render 'offtopic!'
        }

        get('brokers') {
            brokers = new ArrayList()
            curator = null

            try {
                curator = CuratorPool.instance.borrowObject()
                JsonSlurper parser = new JsonSlurper()
                curator.client.getChildren().forPath('/brokers/ids').each { String id ->
                    // Pulling this into a String buffer since parse(byte[]) is
                    // throwing a stackoverflow error
                    buffer = new String(curator.client.getData().forPath("/brokers/ids/${id}"))
                    brokers.add(parser.parseText(buffer))
                }
            }
            finally {
                CuratorPool.instance.returnObject(curator)
            }

            if (request.headers.get('Content-Type') == 'application/json') {
                render(json(brokers))
            }
            else {
                render handlebarsTemplate('brokers.html', brokers: brokers)
            }
        }

        /* set up a demo/dummy websocket listener */
        get("ws") { context ->
            websocket(context) { ws ->
            } connect {
                it.onClose {
                    println "closing"
                } onMessage {
                    "client sent me ${it}"
                }
            }
        }

        assets 'public'
    }
}

