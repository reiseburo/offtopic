package offtopic.curator

import spock.lang.Specification

class CuratorClientInitializationSpec extends Specification {
    def "with null"() {
        when:
        def client = new CuratorClient()

        then:
        thrown(offtopic.errors.ConfigurationError)
    }
    def "with an empty String"() {
        when:
        def client = new CuratorClient('')

        then:
        thrown(offtopic.errors.ConfigurationError)
    }

    def "with a valid String"() {
        when:
        def client = new CuratorClient('localhost:2181')

        then:
        client instanceof CuratorClient
    }
}
