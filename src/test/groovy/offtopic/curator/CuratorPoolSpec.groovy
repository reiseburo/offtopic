package offtopic

import spock.lang.Specification

class CuratorPoolSpec extends Specification {
    def "an instance should be an instance"() {
        expect:
        CuratorPool.instance instanceof CuratorPool
    }
}
