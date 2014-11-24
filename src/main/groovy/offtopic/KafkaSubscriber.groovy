package offtopic

import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import offtopic.curator.CuratorPool

/**
 * KafkaSubscriber is a Kafka consumer consumer, largely cribbed from:
 * https://cwiki.apache.org/confluence/display/KAFKA/Consumer+Group+Example
 */
class KafkaSubscriber {

    String topic
    private Closure callback
    private ConsumerConnector consumer

    public KafkaSubscriber(String topicName) {
        this.topic = topicName
    }

    public void setCallback(Closure theCallback) {
        this.callback = theCallback
    }

    public void connect() {
        this.consumer = kafka.consumer.Consumer.createJavaConsumerConnector(
                        createConsumerConfig('localhost:2181', 'offtopic-spock-test'))
    }

    public void shutdown() {
        this.consumer?.shutdown()
    }

    public void consume() {
        if (this.consumer == null) {
            println "no consumer, gtfo"
            return
        }

        def topicCountMap = new HashMap<String, Integer>()
        topicCountMap.put(this.topic, 1)
        def consumerMap = this.consumer.createMessageStreams(topicCountMap)

        consumerMap.get(this.topic).each { stream ->
            def iterator = stream.iterator()
            while (iterator.hasNext()) {
                def message = iterator.next().message()
                this.callback.call(new String(message))
            }
        }
    }

    private static ConsumerConfig createConsumerConfig(String a_zookeeper,
                                                       String a_groupId) {
        Properties props = new Properties();
        props.put("zookeeper.connect", a_zookeeper);
        props.put("group.id", a_groupId);
        props.put("zookeeper.session.timeout.ms", "5000");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
        return new ConsumerConfig(props);
    }
}
