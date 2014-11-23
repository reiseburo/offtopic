package offtopic.curator

import spock.lang.Specification

class CuratorClientObjectFactorySpec extends Specification {

    def "instantiation with zookeepers"() {
        when:
        def ccof = new CuratorClientObjectFactory('hello')

        then:
        ccof.zookeepers == 'hello'
    }
}
