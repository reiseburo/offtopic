package offtopic

import groovy.util.logging.Slf4j

/**
 * OfftopicClient coordinates the interactions between KafkaSubscriber objects
 * and the websocket interactions
 */
@Slf4j
class OfftopicClient {
    int clientId = 0

    private Closure messageCallback
    private String topicsPattern
    private List<KafkaSubscriber> subscribers
    private Configuration config

     OfftopicClient(Configuration configuration) {
        this.clientId = new Random().nextInt()
        this.config = configuration
        this.subscribers = new ArrayList<KafkaSubscriber>()
     }

    List<KafkaSubscriber> getSubscribers() {
        return this.subscribers
    }

    void createSubscribersFor(String topicsPattern) {
        topicsFrom(topicsPattern).each { topic ->
            if (topic.length() == 0) {
                return
            }

            KafkaSubscriber subscriber =  new KafkaSubscriber(System.getProperty('zookeepers') ?: Configuration.DEFAULT_ZOOKEEPERS,
                                                                topic,
                                                                "offtopic-${clientId}")
            subscriber.callback = this.messageCallback
            this.subscribers.add(subscriber)
        }
    }

    void setOnMessageCallback(Closure c) {
        this.messageCallback = c
    }

    void startSubscribers() {
        this.subscribers.each { subscriber ->
            Thread runner = new Thread({
                subscriber.connect()
                log.info "subscriber connected"
                subscriber.consume()
                log.info "consume over!"
            })
            runner.start()
        }
    }

    void shutdown() {
        this.subscribers.each { subscriber ->
            subscriber.shutdown()
        }
    }

    List <String> topicsFrom(String topicsPattern) {
        List<String> topics = []
        topicsPattern.split("\\+").each { topic ->
            if (topic.length() == 0) {
                return
            }
            if (topic.indexOf('*') >= 0) {
                /* in this case our `topic` is actually a topic pattern */
                topics.addAll(lookupTopicsFor(topic))
            }
            else {
                topics.add(topic)
            }
        }
        return topics
    }

    private List<String> lookupTopicsFor(String topicPattern) {
        List<String> topics = []
        KafkaService.fetchTopics().each { topic ->
            if (topic =~ topicPattern) {
                topics.add(topic)
            }
        }
        return topics
    }
}
