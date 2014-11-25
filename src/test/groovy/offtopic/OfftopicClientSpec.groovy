package offtopic

import spock.lang.*

class OfftopicClientSpec extends Specification {
    def "initialization should create a clientId"() {
        when:
        def client = new OfftopicClient()

        then:
        client.clientId != 0
    }

    def "initialization should create a subscribers ArrayList"() {
        when:
        def client = new OfftopicClient()

        then:
        client.subscribers.size() == 0
    }
}

class OfftopicClientCreateSubscribersSpec extends Specification {
    def client = null

    def setup() {
        this.client = new OfftopicClient()
    }

    def "createSubscribersFor with an empty string"() {
        when:
        this.client.createSubscribersFor('')

        then:
        this.client.subscribers.size() == 0
    }

    def "createSubscribersFor with a single topic"() {
        when:
        this.client.createSubscribersFor('spock-topic')

        then:
        this.client.subscribers.size() == 1
    }
}

class OfftopicClientTopicParsingSpec extends Specification {
    def client = null
    def setup() {
        this.client = new OfftopicClient()
    }

    def "a single topic"() {
        when:
        def topics = this.client.topicsFrom('topic')

        then:
        topics.size() == 1
    }

    def "plus-delimited topics"() {
        when:
        def topics = this.client.topicsFrom('topic+some.topic')

        then:
        topics.size() == 2
    }
}

class OfftopicClientTopicLookupSpec extends Specification {
    def client

    def setup() {
        // Mocking out KafkaService so we don't actually have to hit Zookeeper
        GroovyMock(KafkaService, global: true)
        this.client = new OfftopicClient()
    }

    def "looking up topics that don't exist"() {
        when:
        // Call fetchTopics() once and return an empty Array
        1 * KafkaService.fetchTopics() >> []
        def topics = this.client.lookupTopicsFor('spock.*')

        then:
        topics.size() == 0
    }

    def "looking up topics against topics that exist"() {
        when:
        // Call fetchTopics() once and return an empty Array
        1 * KafkaService.fetchTopics() >> ['foo', 'bar', 'spock.rocks']
        def topics = this.client.lookupTopicsFor('spock.*')

        then:
        topics.size() == 1
    }
}
