package offtopic.curator

import spock.lang.Specification

class CuratorPoolSpec extends Specification {
    def "an instance should be an instance"() {
        when:
        CuratorPool.instance

        then:
        thrown(Exception)
    }
}
