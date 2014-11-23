import ratpack.handlebars.HandlebarsModule
import ratpack.jackson.JacksonModule

import static ratpack.handlebars.Template.handlebarsTemplate
import static ratpack.jackson.Jackson.json
import static ratpack.groovy.Groovy.*
import static ratpack.websocket.WebSockets.websocket

import offtopic.curator.CuratorPool

ratpack {
    bindings {
        add new HandlebarsModule()
        add new JacksonModule()
    }
    handlers {
        get {
            curator = null
            try {
                curator = CuratorPool.instance.borrowObject()
                println curator.client.getChildren().forPath('/')
            }
            finally {
                if (curator != null) {
                    CuratorPool.instance.returnObject(curator)
                }
            }
            render 'offtopic!'
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

