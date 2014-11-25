package offtopic

import spock.lang.Specification

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
