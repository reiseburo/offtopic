package offtopic

/**
 * OfftopicClient coordinates the interactions between KafkaSubscriber objects
 * and the websocket interactions
 */
class OfftopicClient {
    public int clientId = 0

    private Closure messageCallback = null
    private String topicsPattern = null
    private ArrayList<KafkaSubscriber> subscribers = null
    private Configuration config = null

    public OfftopicClient(Configuration configuration) {
        this.clientId = new Random().nextInt()
        this.config = configuration
        this.subscribers = new ArrayList<KafkaSubscriber>()
    }

    public ArrayList<KafkaSubscriber> getSubscribers() {
        return this.subscribers
    }

    public void createSubscribersFor(String topicsPattern) {
        topicsFrom(topicsPattern).each { topic ->
            if (topic.length() == 0) {
                return
            }

            KafkaSubscriber subscriber =  new KafkaSubscriber(Configuration.instance.zookeepers,
                                                                topic,
                                                                "offtopic-${clientId}")
            subscriber.callback = this.messageCallback
            this.subscribers.add(subscriber)
        }
    }

    public void setOnMessageCallback(Closure c) {
        this.messageCallback = c
    }

    public void startSubscribers() {
        this.subscribers.each { subscriber ->
            Thread runner = new Thread({
                subscriber.connect()
                println "subscriber connected"
                subscriber.consume()
                println "consume over!"
            })
            runner.start()
        }
    }

    public void shutdown() {
        this.subscribers.each { subscriber ->
            subscriber.shutdown()
        }
    }

    public ArrayList<String> topicsFrom(String topicsPattern) {
        ArrayList<String> topics = new ArrayList<String>()
        topicsPattern.split("\\+").each { topic ->
            if (topic.length() == 0) {
                return
            }
            if (topic.indexOf('*') >= 0) {
                topics.addAll(lookupTopicsFor(topicsPattern))
            }
            else {
                topics.add(topic)
            }
        }
        return topics
    }

    private ArrayList<String> lookupTopicsFor(String topicPattern) {
        ArrayList<String> topics = new ArrayList<String>()
        KafkaService.fetchTopics().each { topic ->
            if (topic =~ topicPattern) {
                topics.add(topic)
            }
        }
        return topics
    }
}
