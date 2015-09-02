import offtopic.curator.CuratorPool
import org.apache.curator.framework.CuratorFramework
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ratpack.handlebars.HandlebarsModule
import ratpack.jackson.JacksonModule

import java.util.logging.Level

import static ratpack.handlebars.Template.handlebarsTemplate
import static ratpack.jackson.Jackson.json
import static ratpack.groovy.Groovy.*
import static ratpack.websocket.WebSockets.websocket

import offtopic.KafkaService
import offtopic.Configuration
import offtopic.OfftopicClient

ratpack {
    bindings {
        offtopic.Configuration.instance.loadDefaults()
        offtopic.curator.CuratorPool.prepare(System.getProperty('zookeepers') ?: Configuration.instance.zookeepers)
        module HandlebarsModule
        module JacksonModule
    }

    handlers {
        get {
            render handlebarsTemplate('index.html')
        }

        get('topics') {
            List<String> topics
            CuratorPool.withCurator { CuratorFramework cf ->
                topics = KafkaService.fetchTopics(cf)
            }

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
            render handlebarsTemplate('topic-watch.html', name: pathTokens.name, title: request.queryParams.title)
        }

        get('topics/:name/websocket') { ctx ->
            OfftopicClient client = new OfftopicClient(Configuration.instance)
            String grepper = null

            websocket(ctx) { ws ->
                Logger log = LoggerFactory.getLogger('WebSocket')
                log.info('Connected client {}', ws)
                client.onMessageCallback = { m ->
                    log.debug('Callback with {] (grep: {})', m, grepper)
                    if ((grepper == null) || (m.raw =~ grepper)) {
                        ws.send(groovy.json.JsonOutput.toJson(m))
                    }
                }
                client.createSubscribersFor(pathTokens.name)
                log.info('Subscribers created for {}', pathTokens.name)
                client.startSubscribers()
            } connect { sock ->
                sock.onClose {
                    client.shutdown()
                }
                sock.onMessage { msg ->
                    grepper = msg.text
                }
            }
        }

        get('brokers') {
            List<Map> brokers
            CuratorPool.withCurator { CuratorFramework cf ->
                brokers = KafkaService.fetchBrokers(cf)
            }

            if (request.headers.get('Content-Type') == 'application/json') {
                render(json(brokers))
            }
            else {
                render handlebarsTemplate('brokers.html', brokers: brokers)
            }
        }

        fileSystem "public", { f -> f.files() }
    }
}

