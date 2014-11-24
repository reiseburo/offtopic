package offtopic

import groovy.json.JsonSlurper
import offtopic.curator.CuratorPool

/**
 * KafkaService is a helper class to help expose data about Kafka to the
 * Ratpack app
 */
class KafkaService {
    final static String BROKERS_PATH = "/brokers/ids"
    final static String TOPICS_PATH = "/brokers/topics"

    public static ArrayList fetchBrokers() {
        def brokers = new ArrayList()
        def parser = new JsonSlurper()

        withCurator { c ->
            c.getChildren().forPath(BROKERS_PATH).each { String id ->
                // Pulling this into a String buffer since parse(byte[]) is
                // throwing a stackoverflow error
                String buffer = new String(c.getData().forPath("${BROKERS_PATH}/${id}"))
                brokers.add(parser.parseText(buffer))
            }
        }

        return brokers
    }

    public static ArrayList fetchTopics() {
        ArrayList brokers = null
        withCurator { c ->
            brokers = c.getChildren().forPath(TOPICS_PATH)
        }
        return brokers
    }

    private static void withCurator(Closure closure) {
        def curator = null
        try {
            curator = CuratorPool.instance.borrowObject()
            closure.call(curator.client)
        }
        finally {
            CuratorPool.instance.returnObject(curator)
        }
    }
}
