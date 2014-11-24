import ratpack.handlebars.HandlebarsModule
import ratpack.jackson.JacksonModule

import static ratpack.handlebars.Template.handlebarsTemplate
import static ratpack.jackson.Jackson.json
import static ratpack.groovy.Groovy.*
import static ratpack.websocket.WebSockets.websocket

import offtopic.KafkaService
import offtopic.KafkaSubscriber

ratpack {
    bindings {
        add new HandlebarsModule()
        add new JacksonModule()
    }
    handlers {
        get {
            render handlebarsTemplate('index.html')
        }

        get('topics') {
            topics = KafkaService.fetchTopics()
            println topics

            if (request.headers.get('Content-Type') == 'application/json') {
                render(json(topics))
            }
            else {
                render handlebarsTemplate('topics.html', topics: topics)
            }
        }

        get('topics/:name') {
            render "Fetching info for ${pathTokens.name}"
        }

        get('topics/:name/watch') {
            render handlebarsTemplate('topic-watch.html', name: pathTokens.name)
        }

        get('topics/:name/websocket') { ctx ->
            subscriber = new KafkaSubscriber(pathTokens.name)
            runner = new Thread({
                subscriber.connect()
                println "subscriber connected"
                subscriber.consume()
                println "consume over!"
            })

            websocket(ctx) { ws ->
                println "Connected ${ws} ${subscriber}"
                subscriber.callback = { msg ->
                    println "called back with: ${msg}"
                    ws.send(groovy.json.JsonOutput.toJson(['raw' : new String(msg),
                            'b64' : msg.encodeBase64().toString()]))
                    println "sent message"
                }
                runner.start()
            } connect { sock ->
                sock.onClose {
                    println "closing up ${subscriber}"
                    subscriber.shutdown()
                }
                sock.onMessage { msg ->
                    println "client sent ${msg}"
                }
            }
        }

        get('brokers') {
            brokers = KafkaService.fetchBrokers()

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

