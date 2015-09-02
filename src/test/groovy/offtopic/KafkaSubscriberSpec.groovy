package offtopic

import spock.lang.Specification

class KafkaSubscriberSpec extends Specification {
    def "initializing"() {
        when:
        KafkaSubscriber subscriber = new KafkaSubscriber(null, 'spock', null)

        then:
        subscriber instanceof KafkaSubscriber
        subscriber.topic == 'spock'
    }
}
