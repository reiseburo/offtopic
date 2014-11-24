package offtopic

import spock.lang.Specification

class KafkaSubscriberSpec extends Specification {
    def "initializing"() {
        when:
        def subscriber = new KafkaSubscriber('spock')

        then:
        subscriber instanceof KafkaSubscriber
        subscriber.topic == 'spock'
    }
}
