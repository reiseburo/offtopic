package offtopic

import spock.lang.Specification

/**
 */
class OfftopicAppSpec extends Specification {
    protected OfftopicApp app

    def setup() {
        this.app = new OfftopicApp()
    }

    def "getName()"() {
        expect: "should return the String 'Offtopic'"
        app.name == 'Offtopic'
    }
}
