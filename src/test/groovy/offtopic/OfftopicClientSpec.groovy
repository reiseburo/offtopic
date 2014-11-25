package offtopic

import spock.lang.Specification

class OfftopicClientSpec extends Specification {
    def "initialization should create a clientId"() {
        when:
        def client = new OfftopicClient()

        then:
        client.clientId != 0
    }
}
